package com.loyaltyworks.keshavcement.ui.redemptionCatalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentProductBinding


class ProductFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProductBinding

    var mSelectedCategory = "-1"

    var mSelectedPointsRange = ""

    var mSelectedSort = ""

    var searchClicked = false

    var categoryClicked = false

    var pointsRangeClicked = false


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

        binding.categoryRecycler.adapter = CategoryAdapter(/*it.objCatalogueCategoryListJson, mSelectedCategory, this*/)
        binding.productRecycler.adapter = ProductAdapter()

        binding.searchButton.setOnClickListener(this)
        binding.categoryButton.setOnClickListener(this)
        binding.pointsRangeButton.setOnClickListener(this)
        binding.allPoints.setOnClickListener(this)
        binding.under1000.setOnClickListener(this)
        binding.thousandTo4999.setOnClickListener(this)
        binding.fiveThousandTo24999.setOnClickListener(this)
        binding.twentyFiveThousandAndAbove.setOnClickListener(this)
        binding.sortFilter.setOnClickListener(this)

        setSelectedFilterTypeOnBackPress()

        setSelectedPointsRangeOnBackPress()

        setSelectedSortOnBackPress()
    }

    override fun onClick(p0: View?) {

        when (p0!!.id) {

            R.id.search_button -> {
                searchClicked = true
                pointsRangeClicked = false
                categoryClicked = false
                binding.categoryLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.GONE
                binding.searchLayout.visibility = View.VISIBLE
                binding.sortFilter.visibility = View.GONE
                binding.searchButton.setBackgroundResource(R.drawable.selected_filter)
                binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)
                mSelectedSort = "0"
                mSelectedCategory = "-1"
                mSelectedPointsRange = ""
                ProductListing(binding.searchFilter.text.toString())
                CategoryApiCall()
                setSelectedPointsRangeOnBackPress()
                setSelectedSortOnBackPress()
            }

            R.id.category_button -> {
                searchClicked = false
                pointsRangeClicked = false
                categoryClicked = true
                binding.searchLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.GONE
                binding.categoryLayout.visibility = View.VISIBLE
                binding.sortFilter.visibility = View.GONE
                binding.searchFilter.text.clear()
                binding.categoryButton.setBackgroundResource(R.drawable.selected_filter)
                binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)
            }

            R.id.points_range_button -> {
                searchClicked = false
                pointsRangeClicked = true
                categoryClicked = false
                binding.searchLayout.visibility = View.GONE
                binding.categoryLayout.visibility = View.GONE
                binding.pointsRangeLayout.visibility = View.VISIBLE
                binding.sortFilter.visibility = View.VISIBLE
                binding.searchFilter.text.clear()
                binding.pointsRangeButton.setBackgroundResource(R.drawable.selected_filter)
                binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
                binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)
            }

            R.id.allPoints -> {

                binding.allPoints.setBackgroundResource(R.drawable.selected_filter)
                binding.under1000.setBackgroundResource(R.drawable.unselected_category)
                binding.thousandTo4999.setBackgroundResource(R.drawable.unselected_category)
                binding.fiveThousandTo24999.setBackgroundResource(R.drawable.unselected_category)
                binding.twentyFiveThousandAndAbove.setBackgroundResource(R.drawable.unselected_category)

                mSelectedPointsRange = "0-100000"

                ProductListing("")


            }

            R.id.under1000 -> {

                binding.allPoints.setBackgroundResource(R.drawable.unselected_category)
                binding.under1000.setBackgroundResource(R.drawable.selected_filter)
                binding.thousandTo4999.setBackgroundResource(R.drawable.unselected_category)
                binding.fiveThousandTo24999.setBackgroundResource(R.drawable.unselected_category)
                binding.twentyFiveThousandAndAbove.setBackgroundResource(R.drawable.unselected_category)
                mSelectedPointsRange = "0-999"

                ProductListing("")


            }

            R.id.thousandTo4999 -> {

                binding.allPoints.setBackgroundResource(R.drawable.unselected_category)
                binding.under1000.setBackgroundResource(R.drawable.unselected_category)
                binding.thousandTo4999.setBackgroundResource(R.drawable.selected_filter)
                binding.fiveThousandTo24999.setBackgroundResource(R.drawable.unselected_category)
                binding.twentyFiveThousandAndAbove.setBackgroundResource(R.drawable.unselected_category)
                mSelectedPointsRange = "1000-4999"

                ProductListing("")


            }

            R.id.fiveThousandTo24999 -> {

                binding.allPoints.setBackgroundResource(R.drawable.unselected_category)
                binding.under1000.setBackgroundResource(R.drawable.unselected_category)
                binding.thousandTo4999.setBackgroundResource(R.drawable.unselected_category)
                binding.fiveThousandTo24999.setBackgroundResource(R.drawable.selected_filter)
                binding.twentyFiveThousandAndAbove.setBackgroundResource(R.drawable.unselected_category)
                mSelectedPointsRange = "5000-24999"

                ProductListing("")


            }

            R.id.twentyFiveThousandAndAbove -> {

                binding.allPoints.setBackgroundResource(R.drawable.unselected_category)
                binding.under1000.setBackgroundResource(R.drawable.unselected_category)
                binding.thousandTo4999.setBackgroundResource(R.drawable.unselected_category)
                binding.fiveThousandTo24999.setBackgroundResource(R.drawable.unselected_category)
                binding.twentyFiveThousandAndAbove.setBackgroundResource(R.drawable.selected_filter)
                mSelectedPointsRange = "25000-10000"

                ProductListing("")


            }

            R.id.sortFilter -> {
                if (binding.sortFilter.text.toString().contentEquals("High to Low")) {

                    binding.sortFilter.text = "Low to High"
                    mSelectedSort = "1"
                    ProductListing("")


                } else {
                    binding.sortFilter.text = "High to Low"
                    mSelectedSort = "0"
                    ProductListing("")

                }
            }

        }

    }

    private fun ProductListing(toString: String) {

    }

    private fun CategoryApiCall() {
        
    }


    private fun setSelectedSortOnBackPress() {

        if (mSelectedSort == "0") {

            binding.sortFilter.text = "High to Low"
        } else if (mSelectedSort == "1") {

            binding.sortFilter.text = "Low to high"
        }

    }

    private fun setSelectedPointsRangeOnBackPress() {

        if (mSelectedPointsRange == "0-10000") {
            binding.allPoints.setBackgroundResource(R.drawable.selected_filter)
        } else if (mSelectedPointsRange == "0-999") {
            binding.under1000.setBackgroundResource(R.drawable.selected_filter)
        } else if (mSelectedPointsRange == "1000-4999") {
            binding.thousandTo4999.setBackgroundResource(R.drawable.selected_filter)
        } else if (mSelectedPointsRange == "5000-24999") {
            binding.fiveThousandTo24999.setBackgroundResource(R.drawable.selected_filter)
        } else if (mSelectedPointsRange == "25000-100000") {
            binding.twentyFiveThousandAndAbove.setBackgroundResource(R.drawable.selected_filter)
        } else {
            binding.allPoints.setBackgroundResource(R.drawable.selected_filter)
            binding.under1000.setBackgroundResource(R.drawable.unselected_category)
            binding.thousandTo4999.setBackgroundResource(R.drawable.unselected_category)
            binding.fiveThousandTo24999.setBackgroundResource(R.drawable.unselected_category)
            binding.twentyFiveThousandAndAbove.setBackgroundResource(R.drawable.unselected_category)
        }

    }

    private fun setSelectedFilterTypeOnBackPress() {

        if (searchClicked) {

            binding.searchButton.setBackgroundResource(R.drawable.selected_filter)
            binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.categoryLayout.visibility = View.GONE
            binding.pointsRangeLayout.visibility = View.GONE
            binding.searchLayout.visibility = View.VISIBLE

        } else if (categoryClicked) {

            binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.categoryButton.setBackgroundResource(R.drawable.selected_filter)
            binding.pointsRangeButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.categoryLayout.visibility = View.VISIBLE
            binding.pointsRangeLayout.visibility = View.GONE
            binding.searchLayout.visibility = View.GONE

        } else if (pointsRangeClicked) {
            binding.searchButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.categoryButton.setBackgroundResource(R.drawable.unselected_filter)
            binding.pointsRangeButton.setBackgroundResource(R.drawable.selected_filter)
            binding.pointsRangeLayout.visibility = View.VISIBLE
            binding.categoryLayout.visibility = View.GONE
            binding.searchLayout.visibility = View.GONE

        }

    }


}