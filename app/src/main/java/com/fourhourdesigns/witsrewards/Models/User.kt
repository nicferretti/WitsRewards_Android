package com.fourhourdesigns.witsrewards.Models

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class User {

    var id: String
    var studentNumber: String
    var firstName: String
    var surname: String
    var yos: String
    var level: String
    var academiaPoints: Int
    var businessPoints: Int
    var universityPoints: Int


    companion object {
        var current: User = User()
    }

    constructor() {
        id = ""
        studentNumber = ""
        firstName = ""
        surname = ""
        yos = ""
        level = ""
        academiaPoints = 0
        businessPoints = 0
        universityPoints = 0
    }

    constructor(userDocument: DocumentSnapshot) {
        id = userDocument.id
        studentNumber = userDocument.getString("studentNumber") ?: "Missing student number"
        firstName = userDocument.getString("name") ?: "First name not found"
        surname = userDocument.getString("surname") ?: "Surname not found"
        yos = userDocument.getString("yos") ?: "yos not found"
        level = userDocument.getString("level") ?: "level not found"
        academiaPoints = (userDocument.getDouble("academiaPoints") ?: 0.0).toInt()
        businessPoints = (userDocument.getDouble("businessPoints") ?: 0.0).toInt()
        universityPoints = (userDocument.getDouble("universityPoints") ?: 0.0).toInt()
    }

    fun addUniversityPoints(points: Int, completion: (success: Boolean, error: Error?) -> Unit) {
        universityPoints += points
        FirebaseFirestore.getInstance().collection("users").document(id).update(
            "universityPoints", universityPoints
        ).addOnSuccessListener {
            completion(true, null)
        }.addOnFailureListener {
            completion(false, Error(it.localizedMessage))
        }
    }
}