package com.example.apartmentsecurity.domain

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthenticator {

        suspend fun signUpWithEmailPassword(email:String , password:String) : FirebaseUser?

        suspend fun signInWithEmailPassword(email: String , password: String): FirebaseUser?

        suspend fun sendPasswordReset(email :String)

        fun signOut() : FirebaseUser?

        fun getUser() : FirebaseUser?

}