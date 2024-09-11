package com.example.apartmentsecurity.domain

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthenticator {

        suspend fun signUpWithEmailPassword(email:String , password:String) : AuthResult?

        suspend fun signInWithEmailPassword(email: String , password: String): AuthResult?

        suspend fun sendPasswordReset(email :String)

        fun signOut() : FirebaseUser?

        fun getUser() : FirebaseUser?

}