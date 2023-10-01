package com.loyaltyworks.keshavcement.ui.notification

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentNotificationBinding
import com.loyaltyworks.keshavcement.ui.DashboardActivity
import com.loyaltyworks.keshavcement.ui.splashScreen.SplashActivity
import com.loyaltyworks.keshavcement.utils.PreferenceHelper


class NotificationFragment : Fragment() {
    private lateinit var _binding: FragmentNotificationBinding
    private val binding get() = _binding!!

    /* ELEMENTS NEEDY TO PUSH NOTIFICATIONS */
    var PUSH_TOKEN = "PUSH_TOKEN"
    var SCREENING = "ActionType"
    var IMAGE_URL = "image_url"
    var PUSH_USER_ACTION_ID = "PushUserActionID"
    var ID = "id"
    var TITLE = "title"
    var BIG_TEXT = "big_text"
    var PRODUCT_ID = "PRODUCT_ID"
    var PROMOTION_ID = "PROMOTION_ID"

    // intent for dashboard
    val DashBoardFragment = "DashBoardFragment"
    val DashBoard = "DashBoard"
    val Support = "Support"
    val ChatPage = "ChatPage"
    val ChatID = "ChatID"

//    private var SCREENING = "ActionType"
//    private var IMAGE_URL = "image_url"
//    private var PUSH_USER_ACTION_ID = "PushUserActionID"
//    private var ID = "id"
//    private var TITLE = "title"
//    private var BIG_TEXT = "big_text"

    var OPEN_APP = 1
    var OPEN_LIST = 2
    var OPEN_DETAILS = 3

    var page = 0
    var mUserActionId = 0
    var mPushCatId = 0


    val PROMOTION_ACTION_TYPE = 101
    val CATALOGUE_ACTION_TYPE = 100
    val Query_TYPE = 103
    val HistoryNotification_TYPE = 102

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val iin = requireActivity().intent
        if (iin != null) {
            page = iin.getStringExtra(SCREENING)!!.toInt()
            mUserActionId = iin.getStringExtra(PUSH_USER_ACTION_ID)!!.toInt()
            mPushCatId = iin.getStringExtra(ID)!!.toInt()
        }

        if (PreferenceHelper.getBooleanValue(requireContext(), BuildConfig.IsLoggedIn)) {
            when (page) {
                Query_TYPE -> openQueryChat()
//                PROMOTION_ACTION_TYPE -> openPromotionModel()
//                CATALOGUE_ACTION_TYPE -> openProductCatalogueModel()
//                HistoryNotification_TYPE -> openHistoryNotification()
                else -> {
                    startActivity(Intent(requireActivity(), DashboardActivity::class.java))
                    requireActivity().finish()
                }
            }
        } else {
            startActivity(Intent(requireActivity(), SplashActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun openQueryChat() {
        when (mUserActionId) {
            OPEN_APP -> {
                val intent = Intent(requireActivity(), DashboardActivity::class.java)
                intent.putExtra(DashBoardFragment, DashBoard)
                startActivity(intent)
                requireActivity().finish()
            }
            OPEN_LIST -> {
                findNavController().navigate(R.id.historyNotificationFragment)
//                val intent = Intent(this, HistoryNotificationActivity::class.java)
//                intent.putExtra(DashBoardFragment, Support)
//                startActivity(intent)
//                finish()
            }
            OPEN_DETAILS -> {
                val intent = Intent(requireActivity(), DashboardActivity::class.java)
                intent.putExtra(DashBoardFragment, ChatPage)
                intent.putExtra(ChatID, mPushCatId)
                startActivity (intent)
                requireActivity().finish ()
            }
        }
    }


}