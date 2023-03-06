package com.loyaltyworks.keshavcement.ui.terms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentTermsAndConditionBinding
import com.loyaltyworks.keshavcement.model.TermsConditionRequest
import com.loyaltyworks.keshavcement.ui.login.fragment.LoginRegistrationViewModel
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.loyaltyworks.keshavcement.utils.language_locale.LocaleManager


class TermsAndConditionFragment : Fragment() {
    private lateinit var _binding: FragmentTermsAndConditionBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginRegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)
        _binding = FragmentTermsAndConditionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*** Firebase Analytics Tracker ***/
        val bundle1 = Bundle()
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "TermsConditionView")
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "TermsConditionFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1)


        binding.btnLayout.visibility = View.GONE

        val mWebview = binding.webview

        val webSetting: WebSettings = mWebview.settings
        webSetting.builtInZoomControls = false
        webSetting.javaScriptEnabled = true
        webSetting.allowContentAccess = true
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        val res =requireContext().resources
        val fontSize = res.getDimension(R.dimen.text).toInt()
        webSetting.defaultFontSize = fontSize as Int

        mWebview.webViewClient = WebViewClient()


        termsConditionApi()
    }

    private fun termsConditionApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getTCData(
            TermsConditionRequest(
                actionType = 1,
                actorId = 2
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*** Terms Condition Observer ***/
        viewModel.termsConditionLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.lstTermsAndCondition.isNullOrEmpty()){

                if (LocaleManager.getLanguagePref(context).equals(LocaleManager.ENGLISH,true)) {
                    for (i in it.lstTermsAndCondition!!){
                        if (i.language.equals("English") && i.html != null && !i.html.isNullOrEmpty()){
//                            binding.webview.loadData(i.html!!,"text/html", "UTF-8")
                            binding.webview.loadDataWithBaseURL(null, i.html!!,null,"text/html", "UTF-8")
                        }
                    }

                }else if (LocaleManager.getLanguagePref(context).equals(LocaleManager.HINDI,true)){
                    for (i in it.lstTermsAndCondition!!){
                        if (i.language.equals("Hindi") && i.html != null && !i.html.isNullOrEmpty()){
                            binding.webview.loadDataWithBaseURL(null, i.html!!,null,"text/html", "UTF-8")
                        }
                    }
                }else if (LocaleManager.getLanguagePref(context).equals(LocaleManager.KANNADA,true)){
                    for (i in it.lstTermsAndCondition!!){
                        if (i.language.equals("Kannada") && i.html != null && !i.html.isNullOrEmpty()){
                            binding.webview.loadDataWithBaseURL(null, i.html!!,null,"text/html", "UTF-8")
                        }
                    }
                }

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })
    }
}