package com.loyaltyworks.keshavcement.baseClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    // call the services on internet present
    abstract fun callInitialServices()

    // call the observers onCreate
    abstract fun callObservers()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    companion object {
        private const val TAG = "BaseFragment"
    }






}