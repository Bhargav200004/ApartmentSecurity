package com.example.apartmentsecurity.data.authentication

import com.example.apartmentsecurity.domain.FirebaseAuthenticator
import com.example.apartmentsecurity.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseAuthenticatorImpl @Inject constructor(
    private val auth : FirebaseAuth?
): FirebaseAuthenticator {
    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?{
        val result = auth?.createUserWithEmailAndPassword(email, password)?.await()
        return result?.user!!
    }

    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
            val result = auth?.signInWithEmailAndPassword(email, password)?.await()
            return result?.user!!
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