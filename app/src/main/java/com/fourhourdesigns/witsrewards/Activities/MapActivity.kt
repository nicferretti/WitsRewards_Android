package com.fourhourdesigns.witsrewards.Activities

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.fourhourdesigns.witsrewards.App
import com.fourhourdesigns.witsrewards.Extensions.distanceMFrom
import com.fourhourdesigns.witsrewards.Helper.LocationHelper
import com.fourhourdesigns.witsrewards.Helper.LocationHelperDelegate
import com.fourhourdesigns.witsrewards.Models.User
import com.fourhourdesigns.witsrewards.Models.Venues
import com.fourhourdesigns.witsrewards.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity: AppCompatActivity(), LocationHelperDelegate {

    val locationPermissionRequestId = 236

    private lateinit var mapView: GoogleMap
    private var isMapReady = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        LocationHelper.shared.delegate = this
        checkPermissions()
        setupMapView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //check if granted permission is for location
        if (requestCode == locationPermissionRequestId) {
            //check if user granted permission to use gps
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationHelper.shared.startUpdatingLocation()
            }
        }
    }

    override fun currentLocationChanged(location: LatLng) {
        if (isMapReady) {
            mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionRequestId)
        } else {
            LocationHelper.shared.startUpdatingLocation()
        }
    }

    private fun setupMapView() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { mapView ->
            this.mapView = mapView
            isMapReady = true

            if (ContextCompat.checkSelfPermission(App.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mapView.isMyLocationEnabled = true

                //move button to bottom right
                val locationButton = (mapFragment.view!!.findViewById<View>(1.toInt()).parent as View).findViewById<View>(2.toInt())
                val rlp = locationButton.layoutParams as RelativeLayout.LayoutParams

                rlp.width = 200
                rlp.height = 200
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                rlp.setMargins(0, 0, 30, 30)
            }

            fetchVenuesIfNeeded()
        }
    }

    private fun fetchVenuesIfNeeded() {
        if (Venues.venues.isEmpty()) {
            Venues.fetchAllVenues { success, error ->
                if (error != null) {
                    println("Error fetching venue: ${error.localizedMessage}")
                } else {
                    addVenueMarkers()
                }
            }
        } else {
            addVenueMarkers()
        }
    }

    private fun addVenueMarkers() {
        mapView.clear()

        for (venue in Venues.venues) {
            val venueMarkerOptions = MarkerOptions()
            venueMarkerOptions.position(venue.coordinates)

            val venueMarker = mapView.addMarker(venueMarkerOptions)
            venueMarker.tag = venue.id

            mapView.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {

                override fun onMarkerClick(marker: Marker): Boolean {
                    val venueId = marker.tag as String
                    val venue = Venues.venues.first { it.id == venueId }


                    val dialogBuilder = AlertDialog.Builder(this@MapActivity)
                    dialogBuilder.setMessage("Do you want to check into this venue?")
                        .setCancelable(true)
                        .setPositiveButton("Check In",  { _, _ ->
                            if (venue.coordinates.distanceMFrom(LocationHelper.shared.currentLocation) <= 50) {
                                User.current.addUniversityPoints(2) { success, error ->
                                    if (success) {
                                        Toast.makeText(this@MapActivity, "Successfully checked in. Added 2 points.", Toast.LENGTH_LONG).show()
                                    } else {
                                        if (error != null) {
                                            Toast.makeText(this@MapActivity, "Error checking in: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(this@MapActivity, "Unable to check in from outside venue's vacinity.", Toast.LENGTH_LONG).show()
                            }
                        })
                        .setNegativeButton("Cancel", { dialog, _ ->
                            dialog.cancel()
                        })

                    val alert = dialogBuilder.create()
                    alert.setTitle(venue.name)
                    alert.show()

                    val postiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
                    postiveButton.setTextColor(Color.BLUE)

                    val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
                    negativeButton.setTextColor(Color.RED)

                    return true
                }
            })
        }
    }
}