package com.example.apartmentsecurity.data.authentication

import com.example.apartmentsecurity.domain.FirebaseAuthenticator
import com.example.apartmentsecurity.util.await
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseAuthenticatorImpl @Inject constructor(
    private val auth : FirebaseAuth?
): FirebaseAuthenticator {
    override suspend fun signUpWithEmailPassword(email: String, password: String): AuthResult?{
        return auth?.createUserWithEmailAndPassword(email, password)?.await()

    }

    override suspend fun signInWithEmailPassword(email: String, password: String): AuthResult? {
            return auth?.signInWithEmailAndPassword(email, password)?.await()
    }

    override fun signOut(): FirebaseUser? {
        val user = auth?.currentUser
        auth?.signOut()
        return user
    }

    override fun getUser(): FirebaseUser? {
        val user = auth?.currentUser
        return user
    }

    override suspend fun sendPasswordReset(email: String) {
        TODO("Not yet implemented")
    }
}