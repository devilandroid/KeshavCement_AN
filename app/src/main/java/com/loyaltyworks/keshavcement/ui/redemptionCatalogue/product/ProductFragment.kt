package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentProductBinding
import com.loyaltyworks.keshavcement.model.PointRange
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.CategoryAdapter
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.PointRangeAdapter
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter.ProductAdapter


class ProductFragment : Fragment(), View.OnClickListener, PointRangeAdapter.OnItemClickCallBack, ProductAdapter.OnItemClickCallBack {

    private lateinit var binding: FragmentProductBinding


    var mSelectedCategory = -1
    var mSelectedCategoryName = ""

    var pointRange = ""
    var LowToHigh = ""

    var isCategoryButtonClicked: Boolean = false
    var isPointRangeButtonClicked: Boolean = false
    var isHighToLowButtonClicked: Boolean = false

    var poitrangeList: ArrayList<PointRange> = ArrayList<PointRange>()
    var mSelectedPointRange = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "ProductView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ProductFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        if (isHighToLowButtonClicked){
            binding.hightoLowLowtoHight.setBackgroundResource(R.drawable.selected_filter)
            binding.hightoLowLowtoHight.setTextColor(requireContext().resources.getColor(R.color.dark))

            if (!LowToHigh.isNullOrEmpty()){
                if (LowToHigh == "0"){
                    binding.hightoLowLowtoHight.text = "High to Low"
                }else if (LowToHigh == "1"){
                    binding.hightoLowLowtoHight.text = "Low to High"
                }
            }

        }

        initialButtonSetup()

        binding.categoryRecycler.adapter = CategoryAdapter(/*it.objCatalogueCategoryListJson, mSelectedCategory, this*/)
        binding.productRecycler.adapter = ProductAdapter(this)

        binding.searchButton.setOnClickListener(this)
        binding.categoryButton.setOnClickListener(this)
        binding.pointsRangeButton.setOnClickListener(this)
        binding.hightoLowLowtoHight.setOnClickListener(this)

        binding.backButton.setOnClickListener(this)
        binding.cartButton.setOnClickListener(this)

        PointRangeSetUp()

    }

    private fun PointRangeSetUp() {
        poitrangeList.clear()

        val defaultstatus = PointRange()
        defaultstatus.id = -1
        defaultstatus.name = "All Points"

        val defaultstatus1 = PointRange()
        defaultstatus1.id = 1
        defaultstatus1.name = "Under 1000 pts"

        val defaultstatus2 = PointRange()
        defaultstatus2.id = 2
        defaultstatus2.name = "1000 - 4999 pts"

        val defaultstatus3 = PointRange()
        defaultstatus3.id = 3
        defaultstatus3.name = "5000 - 24999 pts"

        val defaultstatus4 = PointRange()
        defaultstatus4.id = 4
        defaultstatus4.name = "25000 pts & Above"

        poitrangeList.add(defaultstatus)
        poitrangeList.add(defaultstatus1)
        poitrangeList.add(defaultstatus2)
        poitrangeList.add(defaultstatus3)
        poitrangeList.add(defaultstatus4)

        binding.pointRangeRecycler.adapter = PointRangeAdapter(poitrangeList,mSelectedPointRange,this)
    }

    private fun initialButtonSetup() {

        if (isCategoryButtonClicked){
            binding.searchLayout.visibility = View.GONE
            binding.pointsRangeLayout.visibility = View.GONE
            binding.categoryLayout.visibility = View.VISIBLE
            binding.searchFilter.text.clear()
            binding.categoryButton.setBackgroundResource(R.drawable.selected_filter)
            binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)

        }else if (isPointRangeButtonClicked){
            binding.searchLayout.visibility = View.GONE
            binding.categoryLayout.visibility = View.GONE
            binding.pointsRangeLayout.visibility = View.VISIBLE
            binding.searchFilter.text.clear()
            binding.pointsRangeButton.setBackgroundResource(R.drawable.selected_filter)
            binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)

            if (mSelectedCategory != -1){
                binding.selectedCategoryLayout.visibility = View.VISIBLE
                binding.selectedCategory.text = mSelectedCategoryName
            }else{
                binding.selectedCategoryLayout.visibility = View.INVISIBLE
            }
        }else{
            binding.searchLayout.visibility = View.VISIBLE
            binding.categoryLayout.visibility = View.GONE
            binding.pointsRangeLayout.visibility = View.GONE
            binding.searchFilter.text.clear()
            binding.searchButton.setBackgroundResource(R.drawable.selected_filter)
            binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)

        }
    }

    override fun onClick(p0: View?) {

        when (p0!!.id) {

            R.id.search_button -> {
                LowToHigh = ""
                pointRange = ""
                mSelectedCategory = -1
                mSelectedPointRange = -1
                isCategoryButtonClicked = false
                isPointRangeButtonClicked = false
                isHighToLowButtonClicked = false

                binding.hightoLowLowtoHight.setBackgroundResource(R.drawable.unselected_category)

                binding.categoryLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.GONE
                binding.searchLayout.visibility = View.VISIBLE
                binding.hightoLowLowtoHight.visibility = View.GONE
                binding.selectedCategoryLayout.visibility = View.INVISIBLE
                binding.searchFilter.text.clear()

                binding.searchButton.setBackgroundResource(R.drawable.selected_filter)
                binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)

            }

            R.id.category_button -> {
                LowToHigh = ""
                pointRange = ""

                binding.hightoLowLowtoHight.setBackgroundResource(R.drawable.unselected_category)
                isCategoryButtonClicked = true
                isPointRangeButtonClicked = false
                isHighToLowButtonClicked = false

                binding.searchLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.GONE
                binding.categoryLayout.visibility = View.VISIBLE
                binding.hightoLowLowtoHight.visibility = View.GONE
                binding.selectedCategoryLayout.visibility = View.INVISIBLE
                binding.searchFilter.text.clear()
                binding.categoryButton.setBackgroundResource(R.drawable.selected_filter)
                binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)
            }

            R.id.points_range_button -> {
                isPointRangeButtonClicked = true
                isCategoryButtonClicked = false

                binding.searchLayout.visibility = View.GONE
                binding.categoryLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.VISIBLE
                binding.hightoLowLowtoHight.visibility = View.VISIBLE
                binding.searchFilter.text.clear()
                binding.pointsRangeButton.setBackgroundResource(R.drawable.selected_filter)
                binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)

                if (mSelectedCategory != -1){
                    binding.selectedCategoryLayout.visibility = View.VISIBLE
                    binding.selectedCategory.text = mSelectedCategoryName
                }else{
                    binding.selectedCategoryLayout.visibility = View.INVISIBLE
                }

                mSelectedPointRange = -1
                PointRangeSetUp()

            }

            R.id.hightoLowLowtoHight -> {
                isHighToLowButtonClicked = true
                binding.hightoLowLowtoHight.setBackgroundResource(R.drawable.selected_filter)
                binding.hightoLowLowtoHight.setTextColor(requireContext().resources.getColor(R.color.dark))
                if (binding.hightoLowLowtoHight.text.toString() == "Low to High") {
                    binding.hightoLowLowtoHight.text = "High to Low"
                    LowToHigh = "0"
                } else {
                    binding.hightoLowLowtoHight.text = "Low to High"
                    LowToHigh = "1"
                }
            }

            R.id.cart_button ->{
                findNavController().navigate(R.id.action_productFragment_to_cartFragment)
            }

            R.id.back_button ->{
                findNavController().popBackStack()
            }


        }

    }





    override fun onPointRangeClickResponse(position: Int, pointRangess: PointRange) {
        mSelectedPointRange = pointRangess.id!!

        if (mSelectedPointRange == -1){
            pointRange = ""
        }else if (mSelectedPointRange == 1){
            pointRange = "0-999"
        }else if (mSelectedPointRange == 2){
            pointRange = "1000-4999"
        }else if (mSelectedPointRange == 3){
            pointRange = "5000-24999"
        }else if (mSelectedPointRange == 4){
            pointRange = "25000-9999999"
        }
    }

    override fun onProductListItemClickResponse(itemView: View, position: Int) {
//        val bundle = Bundle()
//        bundle.putSerializable("CatalogueProduct", objCatalogue)

        findNavController().navigate(R.id.action_productFragment_to_productDetailsFragment, /*bundle*/)
    }


}