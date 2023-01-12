package com.loyaltyworks.keshavcement.ui.worksiteDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentNewWorksiteBinding
import kotlinx.android.synthetic.main.row_history_notification.view.*


class NewWorksiteFragment : Fragment(), View.OnClickListener {

    private lateinit var binding:FragmentNewWorksiteBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewWorksiteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**This is required here for handling action bar navigate up button*/
        setHasOptionsMenu(true)

        HandleOnBackPressed()

        binding.next.setOnClickListener(this)
    }

    /**This is required here for handling action bar navigate up button*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){

            if(binding.workDetailsLayout.visibility == View.VISIBLE){

                binding.workDetailsLayout.visibility = View.GONE
                binding.userDetailsLayout.visibility = View.VISIBLE
                binding.workDetailsProgress.setBackgroundColor(requireContext().resources.getColor(R.color.colorAccent))
                binding.next.text = "Next"

            }else if(binding.userDetailsLayout.visibility == View.VISIBLE){

                binding.userDetailsLayout.visibility = View.GONE
                binding.siteLocationLayout.visibility = View.VISIBLE
                binding.userDetailsProgress.setBackgroundColor(requireContext().resources.getColor(R.color.colorAccent))


            }else if( binding.siteLocationLayout.visibility == View.VISIBLE){

                requireActivity().onBackPressed()
            }

            return true


        }else{

            return super.onOptionsItemSelected(item)
        }

    }

    private fun HandleOnBackPressed() {

        val callback = object:OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

                if(binding.workDetailsLayout.visibility == View.VISIBLE){

                    binding.workDetailsLayout.visibility = View.GONE
                    binding.userDetailsLayout.visibility = View.VISIBLE
                    binding.workDetailsProgress.setBackgroundColor(requireContext().resources.getColor(R.color.colorAccent))
                    binding.next.text = "Next"

                }else if(binding.userDetailsLayout.visibility == View.VISIBLE){

                    binding.userDetailsLayout.visibility = View.GONE
                    binding.siteLocationLayout.visibility = View.VISIBLE
                    binding.userDetailsProgress.setBackgroundColor(requireContext().resources.getColor(R.color.colorAccent))


                }else if( binding.siteLocationLayout.visibility == View.VISIBLE){
                    isEnabled = false
                    findNavController().popBackStack()
                }

            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)

    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.next -> {

                if(binding.siteLocationLayout.visibility == View.VISIBLE){

                    binding.siteLocationLayout.visibility = View.GONE
                    binding.userDetailsLayout.visibility = View.VISIBLE
                    binding.userDetailsProgress.setBackgroundColor(requireContext().resources.getColor(R.color.colorPrimary))

                }else if(binding.userDetailsLayout.visibility == View.VISIBLE){

                    binding.userDetailsLayout.visibility = View.GONE
                    binding.workDetailsLayout.visibility = View.VISIBLE
                    binding.workDetailsProgress.setBackgroundColor(requireContext().resources.getColor(R.color.colorPrimary))
                    binding.next.text = "Submit"

                }else if( binding.workDetailsLayout.visibility == View.VISIBLE){

                    findNavController().popBackStack()
                }


            }
        }

    }


}