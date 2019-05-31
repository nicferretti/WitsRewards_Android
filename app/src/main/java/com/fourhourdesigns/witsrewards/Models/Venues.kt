package com.fourhourdesigns.witsrewards.Models

import com.google.firebase.firestore.FirebaseFirestore

object Venues {
    var venues = arrayListOf<Venue>()

    fun fetchAllVenues(completion: (success: Boolean, error: Error?) -> Unit) {
        val fireStore = FirebaseFirestore.getInstance()

        fireStore.collection("venues").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                if (result != null) {
                    for (document in result.documents) {
                        val newVenue = Venue(document)
                        println("Adding new venue with name: ${newVenue.name} and id: ${newVenue.id}")
                        venues.add(newVenue)
                    }
                    completion(true, null)
                } else {
                    completion(false, Error("Error: result is null"))
                }
            } else {
                completion(false, Error("Error getting venues: ${task.exception ?: ""}"))
            }
        }
    }
}