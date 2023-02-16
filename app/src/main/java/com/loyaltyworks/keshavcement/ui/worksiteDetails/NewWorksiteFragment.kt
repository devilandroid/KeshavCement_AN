package com.loyaltyworks.keshavcement.ui.worksiteDetails

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.FragmentNewWorksiteBinding
import com.loyaltyworks.keshavcement.model.SaveWorksiteRequest
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.ClaimSuccessDialog
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue
import com.permissionx.guolindev.PermissionX
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType
import kotlinx.android.synthetic.main.row_history_notification.view.*


class NewWorksiteFragment : Fragment(),Listener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding:FragmentNewWorksiteBinding
    private lateinit var viewModel: WorksiteViewModel

    private var mProfileImagePath = ""


    var latitude: Double = 0.0
    var longitude: Double = 0.0
    private lateinit var easyWayLocation: EasyWayLocation

    private lateinit var googleMap: GoogleMap
    lateinit var latLng: LatLng
    private lateinit var myMarker: Marker

    var captureLocation:Boolean = false

    var tentativeDate = ""
    var selectedLevelId = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(WorksiteViewModel::class.java)
        binding = FragmentNewWorksiteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Firebase Analytics Tracker **/
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "NewWorksiteView")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "NewWorksiteFragment")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        /**This is required here for handling action bar navigate up button*/
        setHasOptionsMenu(true)
        HandleOnBackPressed()

        binding.next.setOnClickListener(this)
        binding.changeImage.setOnClickListener(this)

        askPermission()
        if (this::easyWayLocation.isInitialized)
            easyWayLocation.startLocation()

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume() // needed to get the map to display immediately

        MapsInitializer.initialize(context)
        binding.captureLoc.setOnClickListener {
            getMAPLocation()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getMAPLocation() {
        captureLocation = true
        binding.mapView.getMapAsync(OnMapReadyCallback { mMap ->
            googleMap = mMap
            val center = CameraUpdateFactory.newLatLng(LatLng(latitude, longitude))
            //                CameraUpdate zoom=CameraUpdateFactory.zoomTo(20);
            googleMap.moveCamera(center)
            //                googleMap.animateCamera(zoom);
            googleMap.setMyLocationEnabled(true)
            /* googleMap.setMinZoomPreference(6.0f);
                googleMap.setMaxZoomPreference(20.0f);*/googleMap.getUiSettings()
            .setScrollGesturesEnabled(false)
            latLng = LatLng(latitude, longitude)
            googleMap.getUiSettings().setZoomControlsEnabled(true)
            googleMap.getUiSettings().setRotateGesturesEnabled(false)
            googleMap.getUiSettings().setMapToolbarEnabled(false)

            // Zoom in, animating the camera.
            googleMap.animateCamera(CameraUpdateFactory.zoomIn())

            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
//                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            myMarker = googleMap.addMarker(
                MarkerOptions().position(latLng).draggable(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(latLng).zoom(12f).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            /*Set latlong on Marker where camera move position stop*/mMap.setOnCameraMoveListener {
            latLng = mMap.cameraPosition.target
            if (myMarker != null) myMarker.setPosition(latLng) else Log.d(
                "TAG",
                "Marker is null"
            )
           /* try {
                requireActivity().runOnUiThread {
//                    address.setText(
//                        AppController.getInstance()
//                            .getAddress(context, latLng.latitude, latLng.longitude)
//                    )
                    var addr = AppController.getAddress(requireContext(), latitude.toDouble(), longitude.toDouble())
                    Toast.makeText(requireContext(), "Address : " + addr, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }*/
            //                        Log.d("TAG","Marker is position"+latLng.latitude+","+latLng.longitude);
        }


        })
    }

    private fun askPermission() {

        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList,
                    "Allow permission to access the location and camera to Keshav Cement",
                    "OK",
                    "Cancel")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {

                    // Start Location access
                    locationAccess()

                } else {
                    // when permission denied
                    findNavController().popBackStack()
                }
            }

    }

    private fun locationAccess() {
        val request = LocationRequest.create().apply {
            interval = 60000
            fastestInterval = 30000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            maxWaitTime = 60000
        }
        easyWayLocation = EasyWayLocation(requireContext(), request, false, false, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                EasyWayLocation.LOCATION_SETTING_REQUEST_CODE -> easyWayLocation.onActivityResult(resultCode)
            }

        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        if (this::easyWayLocation.isInitialized)
            easyWayLocation.startLocation()
    }

    override fun onPause() {
        super.onPause()
        if (this::easyWayLocation.isInitialized)
            easyWayLocation.endUpdates()
    }
    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.change_image ->{
                LoadingDialogue.dismissDialog()
                // Browse Image or Files
                FileSelector.requiredFileTypes(FileType.IMAGES).open(requireActivity(), object :
                    FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        mProfileImagePath = fileSelectorData.responseInBase64!!
//                        fileExtenstion = fileSelectorData.extension!!

                        Log.d("gfdhrgfi", "jf " + mProfileImagePath.toString())

                        binding.siteImage.setImageBitmap(fileSelectorData.thumbnail)

                    }
                })
            }

            R.id.next -> {

                if(binding.siteLocationLayout.visibility == View.VISIBLE){

                    if (!mProfileImagePath.isNullOrEmpty()){
                        if (captureLocation){
                            binding.siteLocationLayout.visibility = View.GONE
                            binding.userDetailsLayout.visibility = View.VISIBLE
                            binding.userDetailsProgress.setBackgroundColor(requireContext().resources.getColor(R.color.colorPrimary))
                        }else{
                            Toast.makeText(requireContext(), "Capture the current location", Toast.LENGTH_SHORT).show()
                        }

                    }else{
                        Toast.makeText(requireContext(), "Capture the image", Toast.LENGTH_SHORT).show()
                    }


                }else if(binding.userDetailsLayout.visibility == View.VISIBLE){

                    if (!binding.ownerName.text.toString().trim().isNullOrEmpty()){
                        if (!binding.ownerMobile.text.toString().trim().isNullOrEmpty()){
                            if (!binding.ownerMobile.text.toString().trim().isNullOrEmpty() && binding.ownerMobile.text.toString().trim().length < 10){

                                binding.ownerMobile.error = getString(R.string.enter_valid_mobile_no)
                                binding.ownerMobile.requestFocus()
                            }else{
                                if (!binding.ownerAddr.text.toString().isNullOrEmpty()){

                                    binding.userDetailsLayout.visibility = View.GONE
                                    binding.workDetailsLayout.visibility = View.VISIBLE
                                    binding.workDetailsProgress.setBackgroundColor(requireContext().resources.getColor(R.color.colorPrimary))
                                    binding.next.text = "Submit"

                                }else{

                                    binding.ownerAddr.error = getString(R.string.enter_owner_address)
                                    binding.ownerAddr.requestFocus()
                                }
                            }

                        }else{

                            binding.ownerMobile.error = getString(R.string.enter_mobile_number)
                            binding.ownerMobile.requestFocus()
                        }

                    }else{

                        binding.ownerName.error = getString(R.string.enter_owner_name)
                        binding.ownerName.requestFocus()
                    }



                }else if( binding.workDetailsLayout.visibility == View.VISIBLE){

                    if (selectedLevelId != -1){
                        if (!tentativeDate.isNullOrEmpty()){

                            createWorksiteApi()

                        }else{
                            Toast.makeText(requireContext(), "Please select tentative date!", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireContext(), "Please select the level", Toast.LENGTH_SHORT).show()
                    }

                }


            }
        }

    }

    private fun createWorksiteApi() {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getSaveWorksiteData(
            SaveWorksiteRequest(
                actionType = "1",
                actorId = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                customerID = PreferenceHelper.getLoginDetails(requireContext())?.userList!![0]!!.userId!!.toString(),
                verification = "1",
                siteImageURl = mProfileImagePath,
                addressLongitude = longitude.toString(),
                addressLatitude = latitude.toString(),
                contactNumber = binding.ownerMobile.text.toString(),
                contactPersonName = binding.ownerName.text.toString(),
                address = binding.ownerAddr.text.toString(),
                contactPersonName1 = binding.engineerMobile.text.toString(),
                contactNumber1 = binding.engineerName.text.toString(),
                worklevel = selectedLevelId,
                tentativeDate = binding.tentativeDateTxt.text.toString(),
                remarks = binding.remarks.text.toString(),
                locationname = AppController.getAddress(requireContext(), latitude, longitude)
            )
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.saveWorksiteLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.returnMessage.isNullOrEmpty()){
                if (it.returnMessage == "1"){
                    ClaimSuccessDialog.showClaimSuccessDialog(requireContext(),true,"Successfully!","Created new worksite",
                        object : ClaimSuccessDialog.ClaimSuccessDialogCallBack{
                            override fun onOk() {
                                findNavController().popBackStack()
                            }

                        })
                }else{
                    Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
            }
        })

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

    override fun locationOn() {

    }

    override fun currentLocation(location: Location?) {
        if (location != null) {
            latitude = location.latitude
            longitude = location.longitude
        }

        Log.d("ebfbrfr","lat : " + latitude + " long : " + longitude)
    }

    override fun locationCancelled() {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}