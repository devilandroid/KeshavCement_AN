package com.loyaltyworks.keshavcement.ui

import android.content.Context
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.loyaltyworks.keshavcement.ApplicationClass
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.baseClass.BaseActivity
import com.loyaltyworks.keshavcement.databinding.ActivityDashboardBinding
import com.loyaltyworks.keshavcement.utils.Count.Companion.setCounting
import kotlinx.android.synthetic.main.appbar_main.view.*
import kotlinx.android.synthetic.main.dashboard_menu.view.*
import java.util.*

class DashboardActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDashboardBinding

    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var destinationId: Int = 0

    var context: Context? = null
    lateinit var notification: MenuItem
    var isDashboard: Boolean = false



    override fun callInitialServices() {

    }

    override fun callObservers() {

    }

    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
            return
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ApplicationClass.mCurrentActivity = this
        context = this

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment,
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        Objects.requireNonNull(supportActionBar)!!
            .setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_group_1009))
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)


        /*** Toolbar Hide show section ***/
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            destinationId = destination.id

            when (destinationId) {


                /* R.id.productFragment -> {

                     // make visible only is condition passes
                     *//*   if (this::notification.isInitialized)
                           notification.isVisible = false*//*

                    binding.root.toolbar_parent.visibility = View.GONE
                    binding.root.coordinator.visibility = View.GONE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }*/

                R.id.dashboardFragment, -> {

                    Objects.requireNonNull(supportActionBar)!!
                        .setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_group_1009))
                    Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)


                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

                    // make visible only is condition passes
                    /*      if (this::notification.isInitialized)
                              notification.isVisible = destinationId == R.id.dashboardFragment2*/
                    if (::notification.isInitialized)
                        notification.isVisible = true
                    isDashboard = true
                    binding.root.toolbar_parent.visibility = View.VISIBLE



                }


                else -> {
                    isDashboard = false
                    // make visible only is condition passes
                    if (::notification.isInitialized)
                        notification.isVisible = false
                    binding.root.toolbar_parent.visibility = View.VISIBLE

                    Objects.requireNonNull(supportActionBar)!!
                        .setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_back_round))
                    Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)

                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    //     binding.contentDashboardLayout.bootomView.bottomNavigation.visibility = View.GONE

                }

            }
        }

        binding.root.mlogout.setOnClickListener(this)
        binding.root.mEditProfile.setOnClickListener(this)
        binding.root.mClaimPurchase.setOnClickListener(this)
        binding.root.mMyPurchaseClaim.setOnClickListener(this)
        binding.root.mRedemptionCatalogue.setOnClickListener(this)
        binding.root.mMyRedemptions.setOnClickListener(this)
        binding.root.mMyEarning.setOnClickListener(this)
        binding.root.mWorkSiteDetails.setOnClickListener(this)
        binding.root.mRefer.setOnClickListener(this)
        binding.root.mHelpline.setOnClickListener(this)
        binding.root.mOffers.setOnClickListener(this)
        binding.root.mQuery.setOnClickListener(this)
        binding.root.mTerms.setOnClickListener(this)


    }

    override fun onResume() {
        super.onResume()
//        NetworkXCore.getNetworkX().restartObservation()
    }

    override fun onPause() {
        super.onPause()
//        NetworkXCore.getNetworkX().cancelObservation()
    }

    override fun onDestroy() {
        super.onDestroy()
        ApplicationClass.mCurrentActivity = null
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        notification = menu.findItem(R.id.notification)

        if (isDashboard){
            notification.isVisible = true
        }else{
            notification.isVisible = false
        }

        /*** Hard Code ***/
            setBadgeCount("6")

        return true
    }

    fun setBadgeCount(count: String?) {
        if (this::notification.isInitialized) {
            val icon = notification.icon as LayerDrawable
            setCounting(this, icon, count!!)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //   items = item
        when (item.itemId) {

            R.id.notification -> {
//                navController.popBackStack()
                navController.navigate(R.id.historyNotificationFragment)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
                return true

            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.mRefer -> {

                navController.navigate(R.id.referFragment)

            }

            R.id.mMyPurchaseClaim -> {

                navController.navigate(R.id.myPurchaseClaimFragment)
            }

            R.id.mWorkSiteDetails -> {

                navController.navigate(R.id.worksiteDetailsFragment)
            }

            R.id.mClaimPurchase -> {

                navController.navigate(R.id.claimPurchaseFragment)
            }

            R.id.mMyRedemptions -> {

                navController.navigate(R.id.myRedemptionFragment)
            }

            R.id.mMyEarning -> {

                navController.navigate(R.id.myEarningFragment)
            }

            R.id.mOffers -> {

                navController.navigate(R.id.promotionsFragment)
            }

            R.id.mQuery -> {

                navController.navigate(R.id.queryListingFragment)
            }

            R.id.mHelpline -> {

                navController.navigate(R.id.helplineFragment)
            }

            R.id.mRedemptionCatalogue -> {

                navController.navigate(R.id.redemptionTypeFragment)
            }
        }

    }

}