package com.fourhourdesigns.witsrewards.Helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.fourhourdesigns.witsrewards.App
import com.google.android.gms.maps.model.LatLng

interface LocationHelperDelegate {
    fun currentLocationChanged(location: LatLng) {}
}

class LocationHelper {

    var context: Context
    var locationManager: LocationManager
    lateinit var locationListener: LocationListener

    var delegate: LocationHelperDelegate? = null

    var currentLocation: LatLng
        set(value) {
            field = value

            if (delegate != null) {
                delegate!!.currentLocationChanged(value)
            }
        }
        get() {
            return field
        }

    companion object {
        var shared = LocationHelper()
    }

    constructor() {
        context = App.context
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setUpLocationListener()
        currentLocation = LatLng(0.0, 0.0)
    }

    private fun setUpLocationListener() {
        locationListener = object : LocationListener {
            override fun onLocationChanged(currentLocation: Location?) {
                if (currentLocation != null) {
                    this@LocationHelper.currentLocation = LatLng(currentLocation.latitude, currentLocation.longitude)
                }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {

            }
        }
    }

    fun startUpdatingLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
    }

    fun getLastKnownLocation(): LatLng? {
        var coordinates: LatLng? = null
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                coordinates = LatLng(location.latitude, location.longitude)
            }
        }
        return coordinates
    }
}