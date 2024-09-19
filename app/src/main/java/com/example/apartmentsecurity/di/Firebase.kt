package com.example.apartmentsecurity.di

import com.example.apartmentsecurity.data.authentication.FirebaseAuthenticatorImpl
import com.example.apartmentsecurity.data.db.FirebaseFireStoreImpl
import com.example.apartmentsecurity.domain.FireStore
import com.example.apartmentsecurity.domain.FirebaseAuthenticator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Firebase {

    @Provides
    @Singleton
    fun getFirebaseAuthInstance() : FirebaseAuth  {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun getFireBaseStore() : FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideAuthRepositoryImp(
        auth : FirebaseAuth
    ) : FirebaseAuthenticator {
        return FirebaseAuthenticatorImpl(auth = auth)
    }

    @Provides
    @Singleton
    fun provideFireStoreRepositoryImp(
        fireStore: FirebaseFirestore
    ) : FireStore {
        return FirebaseFireStoreImpl(db = fireStore)
    }


}