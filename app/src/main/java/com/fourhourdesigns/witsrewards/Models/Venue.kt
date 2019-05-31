package com.fourhourdesigns.witsrewards.Models

import com.google.android.gms.maps.model.LatLng
import com.fourhourdesigns.witsrewards.Extensions.toLatLng
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint



class Venue {
    var id: String
    var name: String
    var coordinates: LatLng

    constructor() {
        id = ""
        name = ""
        coordinates = LatLng(0.0, 0.0)
    }

    constructor(document: DocumentSnapshot) {
        id = document.id
        name = document.getString("name") ?: ""
        coordinates = (document.getGeoPoint("coordinates") ?: GeoPoint(0.0, 0.0)).toLatLng()
    }
}