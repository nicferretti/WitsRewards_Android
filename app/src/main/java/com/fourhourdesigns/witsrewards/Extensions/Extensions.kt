package com.fourhourdesigns.witsrewards.Extensions

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

fun GeoPoint.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}

fun LatLng.distanceMFrom(location: LatLng): Double {
    val earthRadius = 3958.75
    val latitudeDifference = Math.toRadians(location.latitude - this.latitude)
    val longitudeDifference = Math.toRadians(location.longitude - this.longitude)
    val a = Math.sin(latitudeDifference / 2) * Math.sin(latitudeDifference / 2) + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(location.latitude)) *
            Math.sin(longitudeDifference / 2) * Math.sin(longitudeDifference / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
    val distance = earthRadius * c

    val meterConversion = 1609

    return distance * meterConversion
}