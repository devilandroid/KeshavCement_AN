package com.loyaltyworks.keshavcement.ui.support

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentNewTicketBinding
import com.loyaltyworks.keshavcement.model.HelpTopicRequest
import com.loyaltyworks.keshavcement.model.ObjHelpTopic
import com.loyaltyworks.keshavcement.model.SaveNewTicketQueryRequest
import com.loyaltyworks.keshavcement.ui.support.adapter.HelpTopicSpinnersAdapter
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType


class NewTicketFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var _binding: FragmentNewTicketBinding
    private val binding get() = _binding!!

    private lateinit var viewModel: NewTicketViewModel

    private  var fileExtenstion:String = ""
    private var mProfileImagePath = ""

    var actionType = 0

    var helpTopicList = mutableListOf<ObjHelpTopic>()
    lateinit var mSelectedTopic: ObjHelpTopic

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(NewTicketViewModel::class.java)
        _binding = FragmentNewTicketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*** Firebase Analytics Tracker ***/
        val bundle1 = Bundle()
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "NewTicketView")
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "NewTicketFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1)

        binding.browseImg.setOnClickListener(this)
        binding.querySubmit.setOnClickListener(this)
        binding.helpTopicSpinner.onItemSelectedListener = this

        helpApi()
    }

    private fun postQueryApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.saveNewTicketQuery(
            SaveNewTicketQueryRequest(
                ActionType = "0",
                ActorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
//                LoyaltyID = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJsonApi!![0].loyaltyId!!.toString(),
                LoyaltyID = PreferenceHelper.getLoginDetails(requireContext())?.userList?.get(0)?.userName.toString(),
//                CustomerName = PreferenceHelper.getCustomerDetails(requireContext())?.Name.toString(),
//                Email = PreferenceHelper.getCustomerDetails(requireContext())?.EmailId.toString(),
                HelpTopic = mSelectedTopic!!.helpTopicName.toString(),
                HelpTopicID = mSelectedTopic!!.helpTopicId.toString(),
                QueryDetails = binding.queryDetails.text.toString(),
                QuerySummary = binding.querySummary.text.toString(),
                ImageUrl = mProfileImagePath,
                FileType = fileExtenstion,
                IsQueryFromMobile = "true",
                SourceType = "1"

            )
        )
    }

    private fun helpApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getHelpTopicListLiveData(
            HelpTopicRequest(
                actionType ="4",
                isActive = "true",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString()
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /* Help topic observer  */
        viewModel.topicListLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (!it.objHelpTopicList.isNullOrEmpty()){
//                val objHelpTopicList: MutableList<ObjHelpTopic> = ArrayList()

                val objHelpTopic = ObjHelpTopic()

                objHelpTopic.helpTopicId = -1
                objHelpTopic.helpTopicName = "Select Query topic"

                helpTopicList.add(0,objHelpTopic)

                helpTopicList.addAll(it.objHelpTopicList!!)

                binding.helpTopicSpinner.adapter = HelpTopicSpinnersAdapter(requireContext(),helpTopicList)
            }
        })

        /* Save new ticket observer  */
        viewModel.saveNewTicketQueryLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (!it.returnMessage.isNullOrEmpty()){
                Toast.makeText(requireContext(), getString(R.string.query_submitted_successfully), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.browseImg -> activity?.let {
                LoadingDialogue.dismissDialog()
                // Browse Image or Files
                FileSelector.requiredFileTypes(FileType.IMAGES).open(requireActivity(), object :
                    FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        mProfileImagePath = fileSelectorData.responseInBase64!!
                        fileExtenstion = fileSelectorData.extension!!

                        binding.browseImg.visibility = View.VISIBLE
                        binding.setImg.setImageBitmap(fileSelectorData.thumbnail)

                    }
                })

                if(mProfileImagePath.isNullOrEmpty()){
                    LoadingDialogue.dismissDialog()
                }
            }

            R.id.query_submit ->{
                if (BlockMultipleClick.click())return;

                if(actionType==1){
                    if (binding.mobileNumber.text.isNullOrEmpty()) {
                        binding.mobileNumber.error = getString(R.string.enter_mobile_number)
                        binding.mobileNumber.requestFocus()
                        return
                    }
                    else if (binding.mobileNumber.text.toString().startsWith(" ")) {
                        binding.mobileNumber.text.clear()
                        binding.mobileNumber.error = getString(R.string.enter_mobile_number)
                        binding.mobileNumber.requestFocus()
                        return
                    }
                }else if (mSelectedTopic.helpTopicId == -1) {
                    Toast.makeText(requireContext(),getString(R.string.select_topic_to_lodge_a_query), Toast.LENGTH_SHORT).show()
                    return
                } else if (binding.queryDetails.text.isNullOrEmpty()) {
                    binding.queryDetails.error = getString(R.string.enter_query_details)
                    binding.queryDetails.requestFocus()
                    return
                } else if (binding.queryDetails.text.toString().startsWith(" ")) {
                    binding.queryDetails.text.clear()
                    binding.queryDetails.error = getString(R.string.enter_query_details)
                    binding.queryDetails.requestFocus()
                    return
                }else {
                    postQueryApi()
                }

            }



        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mSelectedTopic = parent!!.getItemAtPosition(position) as ObjHelpTopic

        Log.d("aabb",mSelectedTopic.toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}