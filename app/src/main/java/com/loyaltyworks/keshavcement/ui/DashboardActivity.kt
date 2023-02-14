package com.loyaltyworks.keshavcement.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
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
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.baseClass.BaseActivity
import com.loyaltyworks.keshavcement.databinding.ActivityDashboardBinding
import com.loyaltyworks.keshavcement.ui.lanuage.LanguageFragment
import com.loyaltyworks.keshavcement.ui.login.LoginActivity
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.Count.Companion.setCounting
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import kotlinx.android.synthetic.main.appbar_main.view.*
import kotlinx.android.synthetic.main.dashboard_menu.view.*
import java.util.*

class DashboardActivity : BaseActivity(), View.OnClickListener, LanguageFragment.LanguageListener {
    lateinit var binding: ActivityDashboardBinding

    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var destinationId: Int = 0

    var context: Context? = null
    lateinit var notification: MenuItem
    lateinit var logout: MenuItem
    lateinit var language: MenuItem
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

        if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) != BuildConfig.SupportExecutive){
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.dashboardFragment,
                ), binding.drawerLayout
            )
        }else{
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.dashboardFragment,
                )
            )
        }


        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) != BuildConfig.SupportExecutive){
            Objects.requireNonNull(supportActionBar)!!
                .setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_group_1009))
            Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        }



        /*** Toolbar Hide show section ***/
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            destinationId = destination.id

            when (destinationId) {


                 R.id.productFragment,
                 R.id.productDetailsFragment, -> {

                     // make visible only is condition passes
//                        if (this::notification.isInitialized)
//                           notification.isVisible = false

                    binding.root.toolbar_parent.visibility = View.GONE
//                    binding.root.coordinator.visibility = View.GONE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.dashboardFragment, -> {
                    if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) != BuildConfig.SupportExecutive){
                        Objects.requireNonNull(supportActionBar)!!
                            .setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_group_1009))
                        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)

                        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    }else{
                        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    }

                    // make visible only is condition passes
                    /*      if (this::notification.isInitialized)
                              notification.isVisible = destinationId == R.id.dashboardFragment2*/
                    if (::notification.isInitialized)
                        notification.isVisible = true

                    if (::language.isInitialized)
                        language.isVisible = true
                    if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
                        if (::logout.isInitialized)
                            logout.isVisible = true
                    }

                    isDashboard = true
                    binding.root.toolbar_parent.visibility = View.VISIBLE



                }


                else -> {
                    isDashboard = false
                    // make visible only is condition passes
                    if (::notification.isInitialized)
                        notification.isVisible = false

                    if (::logout.isInitialized)
                        logout.isVisible = false

                    if (::language.isInitialized)
                        language.isVisible = false

                    binding.root.toolbar_parent.visibility = View.VISIBLE

                    Objects.requireNonNull(supportActionBar)!!
                        .setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_back_round))
                    Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)

                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    //     binding.contentDashboardLayout.bootomView.bottomNavigation.visibility = View.GONE

                }

            }
        }

        binding.root.my_activity_down_layout.visibility = View.GONE

        binding.root.mlogout.setOnClickListener(this)
        binding.root.dEditProfile.setOnClickListener(this)
        binding.root.dClaimPurchase.setOnClickListener(this)
        binding.root.dMyPurchaseClaim.setOnClickListener(this)
        binding.root.dRedemptionCatalogue.setOnClickListener(this)
        binding.root.dMyRedemptions.setOnClickListener(this)
        binding.root.dMyEarning.setOnClickListener(this)
        binding.root.dWorkSiteDetails.setOnClickListener(this)
        binding.root.dRefer.setOnClickListener(this)
        binding.root.dHelpline.setOnClickListener(this)
        binding.root.dOffers.setOnClickListener(this)
        binding.root.dQuery.setOnClickListener(this)
        binding.root.dTerms.setOnClickListener(this)
        binding.root.dMyActivity.setOnClickListener(this)
        binding.root.dClaimHistory.setOnClickListener(this)
        binding.root.dCashTransferHistory.setOnClickListener(this)
        binding.root.dMySupportExecutive.setOnClickListener(this)
        binding.root.dPendingClaimRequest.setOnClickListener(this)
        binding.root.dCashTransferApproval.setOnClickListener(this)
        binding.root.dEnrollment.setOnClickListener(this)
        binding.root.mlogout.setOnClickListener(this)


        if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) == BuildConfig.Engineer ||
            PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) == BuildConfig.Mason){
            binding.root.dEnrollment.visibility = View.GONE
            binding.root.dPendingClaimRequest.visibility = View.GONE
            binding.root.dCashTransferApproval.visibility = View.GONE
            binding.root.dMyActivity.visibility = View.GONE
            binding.root.dMySupportExecutive.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) == BuildConfig.Dealer){
            binding.root.dClaimPurchase.visibility = View.GONE
            binding.root.dMyPurchaseClaim.visibility = View.GONE
            binding.root.dWorkSiteDetails.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) == BuildConfig.SubDealer){
            binding.root.dWorkSiteDetails.visibility = View.GONE

        }else if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) == BuildConfig.SupportExecutive){

        }

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
        logout = menu.findItem(R.id.logout)
        language = menu.findItem(R.id.language)

        if (isDashboard){
            notification.isVisible = true
            language.isVisible = true
            if (PreferenceHelper.getStringValue(this, BuildConfig.CustomerType) == BuildConfig.SupportExecutive){
                logout.isVisible = true
            }else{
                logout.isVisible = false
            }

        }else{
            notification.isVisible = false
            language.isVisible = false
            logout.isVisible = false
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

            R.id.logout ->{
                PreferenceHelper.clear(this)
                startActivity(Intent(context, LoginActivity::class.java))
                finish()
            }

            R.id.language ->{
                navController.navigate(R.id.languageFragment2)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.dRefer -> {
                navController.navigate(R.id.referFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dMyPurchaseClaim -> {
                navController.navigate(R.id.myPurchaseClaimFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dWorkSiteDetails -> {
                navController.navigate(R.id.worksiteDetailsFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dClaimPurchase -> {
                navController.navigate(R.id.purchaseRequestFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dMyRedemptions -> {
                navController.navigate(R.id.myRedemptionFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dMyEarning -> {
                navController.navigate(R.id.myEarningFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dOffers -> {
                navController.navigate(R.id.promotionsFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dQuery -> {
                navController.navigate(R.id.supportFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dHelpline -> {
                navController.navigate(R.id.helplineFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dRedemptionCatalogue -> {
                navController.navigate(R.id.redemptionTypeFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dMyActivity -> {
                if (BlockMultipleClick.click())return

                if (binding.root.my_activity_down_layout.visibility == View.GONE){
                    binding.root.my_activity_down_layout.visibility = View.VISIBLE
                    binding.root.down_icon.rotation = 180f
                }else{
                    binding.root.my_activity_down_layout.visibility = View.GONE
                    binding.root.down_icon.rotation = 0f
                }
            }

            R.id.dClaimHistory -> {
                navController.navigate(R.id.claimHistoryFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dCashTransferHistory -> {
                navController.navigate(R.id.cashTransferHistoryFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dMySupportExecutive -> {
                navController.navigate(R.id.mySupportExecutiveFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dPendingClaimRequest -> {
                navController.navigate(R.id.pendingClaimRequestFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dCashTransferApproval -> {
                navController.navigate(R.id.cashTransferApprovalFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dEditProfile -> {
                navController.navigate(R.id.profileFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.dEnrollment -> {
                navController.navigate(R.id.enrollmentFragment)
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }

            R.id.mlogout -> {
                PreferenceHelper.clear(this)
                startActivity(Intent(context, LoginActivity::class.java))
                finish()
            }
        }

    }

    override fun onLanguageSelect(language: String) {
        (this as BaseActivity).setNewLocale(context as AppCompatActivity, language)
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}