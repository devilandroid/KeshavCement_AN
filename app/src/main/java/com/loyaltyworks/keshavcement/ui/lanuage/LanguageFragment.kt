package com.loyaltyworks.keshavcement.ui.lanuage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentLanguageBinding

class LanguageFragment : Fragment() {
    private lateinit var binding: FragmentLanguageBinding

    // CallBack interface
    interface LanguageListener{
        fun onLanguageSelect(language : String)
    }

    private lateinit var languageListenerForActivity:LanguageListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languageListenerForActivity = requireActivity() as LanguageListener

        binding.englishLang.setOnClickListener {
            languageListenerForActivity.onLanguageSelect("en")

        }


        binding.hindiLang.setOnClickListener {
            languageListenerForActivity.onLanguageSelect("hi")

        }

        binding.kannadaLang.setOnClickListener {
            languageListenerForActivity.onLanguageSelect("kn")

        }
    }


}