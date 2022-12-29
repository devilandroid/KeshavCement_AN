package com.loyaltyworks.keshavcement.baseClass

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.loyaltyworks.keshavcement.utils.NoInternetDialog
import com.loyaltyworks.keshavcement.utils.internet.ConnectivityReceiver
import com.loyaltyworks.keshavcement.utils.internet.ConnectivityReceiverListener

abstract class BaseActivity : AppCompatActivity(), ConnectivityReceiverListener {




    private var hideNotInternetDialogue = false  // default show no internet dialogue


    lateinit var viewGroup: ViewGroup

    // connectivity Receiver class
    private var connectivityReceiver = ConnectivityReceiver


    // call the services on internet present
    abstract fun callInitialServices()

    // call the observers onCreate
    abstract fun callObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${this.localClassName} : Created Base Activity")
    }




    override fun onStart() {
        super.onStart()
        Log.d(TAG, "${this.localClassName} : OnStart Base Activity")
        // register connectivity receiver
        registerConnectivityReceiver(true)

        callObservers()

        // get view groups
        viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "${this.localClassName} : Pause Base Activity")

    }

    override fun onDestroy() {
        Log.d(TAG, "${this.localClassName} : Destroyed Base Activity")
        registerConnectivityReceiver(false)
        super.onDestroy()
    }

    private fun registerConnectivityReceiver(isRegister: Boolean) {
        if (isRegister) {
            // set network listener callback
            connectivityReceiver.connectivityReceiverListener = this
            // register the network check broadcast receiver
            registerReceiver(
                connectivityReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        } else {

            try {
                // unregister the connectivity receiver
                unregisterReceiver(connectivityReceiver)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }

        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            Log.d(TAG, "${this.localClassName} : onNetworkConnectionChanged: $isConnected")
            // dismiss no internet dialog on internet back
            NoInternetDialog.dismissDialog()
            // call the services on internet back
            callInitialServices()
        } else {
            Log.d(TAG, "${this.localClassName} : onNetworkConnectionChanged: $isConnected")
            if (!hideNotInternetDialogue) {
                // show no internet dialog on no internet
                NoInternetDialog.showDialog(this)
            }
        }

    }




    // optional for display or hide no internet dialogue
    fun hideNoInternetDialog(hide: Boolean) {
        hideNotInternetDialogue = hide
    }

    companion object {
        const val TAG = "BaseActivity"
    }

    // SnackBar display
    @RequiresApi(Build.VERSION_CODES.M)
    fun snackBar(MSG: String, color: Int) {
        Snackbar.make(viewGroup, MSG, Snackbar.LENGTH_LONG).setBackgroundTint(
            getColor(color)
        ).show()
    }


    open fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("TAG", "Permission is granted")
                true
            } else {
                Log.v("TAG", "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    111
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted")
            true
        }
    }


    open fun isAccessFineLocationPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("TAG", "Permission is granted")
                true
            } else {
                Log.v("TAG", "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    121
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted")
            true
        }
    }


    open fun isCourseLocationPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("TAG", "Permission is granted")
                true
            } else {
                Log.v("TAG", "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    123
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted")
            true
        }
    }


    open fun isCameraPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("TAG", "Permission is granted")
                true
            } else {
                Log.v("TAG", "Permission is revoked")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 114)
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted")
            true
        }
    }


    open fun isWriteStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("TAG", "Permission is granted")
                true
            } else {
                Log.v("TAG", "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    113
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted")
            true
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0])
            //resume tasks needing this permission
        }
    }


}