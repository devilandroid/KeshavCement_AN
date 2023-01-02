package com.loyaltyworks.keshavcement.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentHistoryNotificationBinding


class HistoryNotificationFragment : Fragment() {
//    private lateinit var viewModel: HistoryNotificationViewModel
//    private lateinit var historyNotificationAdapter: HistoryNotificationAdapter


    private lateinit var binding: FragmentHistoryNotificationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryNotificationBinding.inflate(layoutInflater)
//        viewModel = ViewModelProvider(this).get(HistoryNotificationViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        binding.noDataFount.noDataFoundLayout.visibility = View.GONE
//        binding.historyRv.visibility = View.VISIBLE
//
//        callApi()
//
//        /* Notification List Observer */
//        viewModel.historyNotificationtLiveData.observe(viewLifecycleOwner,
//            androidx.lifecycle.Observer {
//                if (it != null && !it.lstPushHistoryJson.isNullOrEmpty()) {
//                    historyNotificationAdapter = HistoryNotificationAdapter(
//                        it, object : HistoryNotificationAdapter.ItemClicked {
//                            override fun itemclicks(notificationHistory: LstPushHistoryJson?) {}
//                        })
//
//                    binding.historyRv.adapter = historyNotificationAdapter
//                    binding.noDataFount.noDataFoundLayout.visibility = View.GONE
//                    binding.historyRv.visibility = View.VISIBLE
//                    LoadingDialogue.dismissDialog()
//                } else {
//                    binding.noDataFount.noDataFoundLayout.visibility = View.VISIBLE
//                    binding.historyRv.visibility = View.GONE
//                    LoadingDialogue.dismissDialog()
//                }
//            })
//
//        /* Notification Details Observer */
//        viewModel.historyNotificationtDetailByIDLiveData.observe(viewLifecycleOwner,
//            androidx.lifecycle.Observer {
//                if (it != null && !it.lstPushHistoryJson.isNullOrEmpty()) {
//                    setReadMoreHistoryNotificationObserver()
//                }
//            })
//
//        /* Notification Delete Observer */
//        viewModel.historyNotificationtDeleteByIDLiveData.observe(viewLifecycleOwner,
//            androidx.lifecycle.Observer {
//                if (it != null && it.returnValue!! > 0) {
//                    (activity as DashboardActivity).snackBar(getString(R.string.notification_removed_success),
//                        R.color.green
//                    )
//                } else {
//                    (activity as DashboardActivity).snackBar(getString(R.string.notification_removed_fail),
//                        R.color.primary_darkred2
//                    )
//                }
//            })
//
//        enableSwipeToDeleteAndUndo()

    }

    private fun setReadMoreHistoryNotificationObserver() {
//        viewModel.historyNotificationtDetailByIDLiveData.observe(
//            viewLifecycleOwner,
//            androidx.lifecycle.Observer { })

    }

    private fun enableSwipeToDeleteAndUndo() {
//        val swipeToDeleteCallback: SwipeToDeleteCallback =
//            object : SwipeToDeleteCallback(requireContext()) {
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
//                    val position = viewHolder.adapterPosition
//                    val item: LstPushHistoryJson =
//                        historyNotificationAdapter.getData()?.get(position)!!
//                    historyNotificationAdapter.removeItem(position)
//                    viewModel.getDeleteHistoryNotificationResponse(
//                        HistoryNotificationDetailsRequest(
//                            ActionType = "2",
//                            ActorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0].userId.toString(),
//                            LoyaltyId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0].userName,
//                            PushHistoryIds = item.pushHistoryId.toString()
//                        )
//                    )
//                }
//            }
//
//        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
//        itemTouchhelper.attachToRecyclerView(history_rv)
    }


    private fun callApi() {
//        viewModel.getNotificationHistoryResponse(
//            HistoryNotificationRequest(
//                ActionType = "0",
//                ActorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0].userId.toString(),
//                LoyaltyId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0].userName
//            )
//        )
    }


}