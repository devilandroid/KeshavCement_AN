package com.loyaltyworks.keshavcement.ui.support

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentQueryChatBinding
import com.loyaltyworks.keshavcement.model.ObjCustomerAllQueryJson
import com.loyaltyworks.keshavcement.model.PostChatStatusRequest
import com.loyaltyworks.keshavcement.model.QueryChatElementRequest
import com.loyaltyworks.keshavcement.ui.support.adapter.QueryChatAdapter
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType


class QueryChatFragment : Fragment(), View.OnClickListener, QueryChatAdapter.ChatImageDisplay {
    private lateinit var _binding: FragmentQueryChatBinding
    private val binding get() = _binding!!

    private lateinit var viewModel: QueryChatViewModel

    private var fileExtenstion = ""
    private var mProfileImagePath = ""
    var ticketId: Int = -1
    var customerTicketId: Int = -1
    var actionID: Int = 0
    var actionType: Int = 0
    var QueryStatus: String? = null
    var helpTopic: String? = null
    var helpTopicID: String? = null

    var userID: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(QueryChatViewModel::class.java)
        // Inflate the layout for this fragment
        _binding = FragmentQueryChatBinding.inflate(layoutInflater)

        /*** Firebase Analytics Tracker ***/
        val bundle1 = Bundle()
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "QueryChatView")
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "QueryChatFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1)


        val bundle = this.arguments
        if (bundle != null) {
            val dataholder: ObjCustomerAllQueryJson? =
                arguments?.getSerializable("SUPPORT_DATA") as ObjCustomerAllQueryJson?
            actionID = arguments?.getInt("ActionId")!!

            if (dataholder != null) {
//                ticketId = dataholder.TicketStatus?.toInt()!!
                customerTicketId = dataholder.customerTicketID!!
                QueryStatus = dataholder.ticketStatus!!
                helpTopic = dataholder.helpTopic!!
                helpTopicID = dataholder.helpTopicID.toString()!!
//                binding.querySummary.text = "Ticket Details " + " : " + dataholder.querySummary!!

            }
        }

        binding.imageAdd.setOnClickListener(this)
        binding.sendQueryBtn.setOnClickListener(this)

        loadChats()

        return binding.root
    }

    private fun loadChats() {
        userID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toInt()
        viewModel.getQueryChat(
            QueryChatElementRequest(
                "171", userID.toString(), customerTicketId.toString()

            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (QueryStatus.equals("Closed", true) || actionID == 1) {
            binding.edittextBlockLinear.visibility = View.GONE

            if (actionID == 0)
                binding.chatlistClosetText.visibility = View.VISIBLE


        } else {
            binding.edittextBlockLinear.visibility = View.VISIBLE
            binding.chatlistClosetText.visibility = View.GONE
        }

        binding.closeImage.setOnClickListener {
            if (BlockMultipleClick.click()) return@setOnClickListener
            binding.ChatImageOpen.visibility = View.GONE
            binding.chatListLl.visibility = View.VISIBLE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /* chat message observer */
        viewModel.queryChatLiveData.observe(viewLifecycleOwner, Observer {
            binding.queryChatRecycler.adapter = QueryChatAdapter(it, this)
            binding.imageAdd.isEnabled = true
            binding.sendQueryBtn.isEnabled = true
            binding.queryDetailsFld.isEnabled = true
            binding.queryDetailsFld.text.clear()
            mProfileImagePath = ""
            binding.queryChatRecycler.scrollToPosition(it.objQueryResponseJsonList!!.size - 1)

            LoadingDialogue.dismissDialog()
        })

        /* post chat observer */
        viewModel.postChatStatusResponseLiveData.observe(
            viewLifecycleOwner,
            Observer {
                LoadingDialogue.dismissDialog()
                mProfileImagePath = ""

                if (it != null && !it.returnMessage.isNullOrEmpty() && it.returnMessage!!.toString().split("~")[0] == "1") {
                    Log.d("cccc", it.returnMessage!!.toString().split("~")[0])
                    loadChats()
                } else {
                    /*(activity as DashboardActivity).snackBar(
                        "somthing went wrong",
                        R.color.colorPrimary
                    )*/
                    Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT).show()

                }
            })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.imageAdd ->
                FileSelector.requiredFileTypes(FileType.ALL)
                    .open(requireActivity(), object : FileSelectorCallBack {
                        override fun onResponse(fileSelectorData: FileSelectorData) {
                            mProfileImagePath = fileSelectorData.responseInBase64!!
                            fileExtenstion = fileSelectorData.extension!!
                            // post the image
                            getPostReplyTextImage()
                        }
                    })

            R.id.send_query_btn ->{
//                Keyboard.hideKeyboard(requireContext(), p0)
                getPostReplyTextImage()
            }
        }
    }

    private fun getPostReplyTextImage() {
        if (!TextUtils.isEmpty(
                binding.queryDetailsFld.text.toString().trim()
            ) || !mProfileImagePath.isNullOrEmpty()
        ) {
            LoadingDialogue.showDialog(requireContext())

            when (QueryStatus) {
                "Resolved",
                "Reopen" -> ticketId = 2
                "Resolved-Follow up" -> ticketId = 5
                "Closed" -> ticketId = 4
                "Pending" -> ticketId = 1
            }

            val postChatStatusRequest = PostChatStatusRequest()

            postChatStatusRequest.ActionType = "4"

            if (!mProfileImagePath.isNullOrEmpty()) {
                postChatStatusRequest.ImageUrl = mProfileImagePath
                postChatStatusRequest.FileType = fileExtenstion
                postChatStatusRequest.IsQueryFromMobile = "true"
            } else {
                postChatStatusRequest.IsQueryFromMobile = "false"
            }
            postChatStatusRequest.ActorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId.toString()

            if (!TextUtils.isEmpty(binding.queryDetailsFld.text))
                postChatStatusRequest.QueryDetails = binding.queryDetailsFld.text.toString()

            postChatStatusRequest.HelpTopicID = helpTopicID
            postChatStatusRequest.CustomerTicketID = customerTicketId.toString()
            postChatStatusRequest.QueryStatus = ticketId.toString()
            postChatStatusRequest.HelpTopic = helpTopic

            viewModel.getPostReply(postChatStatusRequest, requireContext())

        } else {
            binding.queryDetailsFld.text.clear()
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "Something went wrong",
                Snackbar.LENGTH_LONG).show()
        }

    }

    override fun onClickChatImage(Url: String?) {
        binding.ChatImageOpen.visibility = View.VISIBLE
        binding.chatListLl.visibility = View.GONE
        binding.ChatImageOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        Glide.with(this)
            .load(Url)
            .placeholder(R.drawable.ic_default_img)
            .error(R.drawable.ic_error)
            .into(binding.chatImges)
    }
}