package com.elabasy.MyWeatherApp.Ui.Splash

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.elabasy.MyWeatherApp.R
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.Window
import androidx.core.app.ActivityCompat
import com.elabasy.MyWeatherApp.Ui.Activities.Home.HomeActivity
import com.elabasy.MyWeatherApp.databinding.ActivitySplashBinding
import com.elabasy.MyWeatherApp.databinding.ItemsDialogMapsBinding
import com.google.android.gms.location.*
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val PERMISSION_ID = 1010
    private val HANDLER_TIME: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set status background white
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        //to make full screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.setFlags(
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            )
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        Handler(Looper.getMainLooper()).postDelayed({
            requestPermission()
        }, HANDLER_TIME)

    }


    private fun checkPermission(): Boolean {
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if (
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false

    }

    private fun requestPermission() {
        //this function will allows us to tell the user to request the necessary Permission if they are not granted
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    private fun isLocationEnabled(): Boolean {
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                moveToHome("London")
            }

        }
    }

    private fun getLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        val cityName = getCityName(location.latitude, location.longitude)
                        if (cityName.isNotEmpty() || !cityName.equals(null)) {
                            moveToHome(cityName)
                        }
                    }
                }
            } else {
                showDialogs()
            }
        } else {
            requestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun newLocationData() {
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            val cityName = getCityName(lastLocation.latitude, lastLocation.longitude)
            if (cityName.isNotEmpty() || !cityName.equals(null)) {
                moveToHome(cityName)
            }
        }
    }


    private fun getCityName(lat: Double, long: Double): String {
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 3)
        return address[0].countryName
    }

    private fun showDialogs() {
        val dialog = Dialog(this)
        val bindingDialog: ItemsDialogMapsBinding =
            ItemsDialogMapsBinding.inflate(LayoutInflater.from(this))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        bindingDialog.mapsAgree.setOnClickListener {
            startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1)
            dialog.dismiss()
        }
    }

    private fun moveToHome(cityName: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("CityName", cityName)
        startActivity(intent)
        finish()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            requestPermission()
        }
    }


}





