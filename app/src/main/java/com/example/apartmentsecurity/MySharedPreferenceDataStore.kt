package com.example.apartmentsecurity

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


val Context.myPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = "my_preference")

enum class AuthenticationType {
    ADMIN,
    USER,
    SECURITY,
    UNAUTHENTICATED
}

data class PreferenceData(
    val name: String,
    val apartmentName: String,
    val apartmentId: String,
    val roomNumber: String?,
    val authenticationType: AuthenticationType
)

@Singleton
class MySharedPreferenceDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val myPreferenceDataStore = context.myPreferenceDataStore

    private object PreferenceKeys {
        val NAME = stringPreferencesKey("name")
        val APARTMENT_NAME = stringPreferencesKey("apartment_name")
        val APARTMENT_ID = stringPreferencesKey("apartment_id")
        val ROOM_NUMBER = stringPreferencesKey("room_number")
        val AUTHENTICATION_TYPE = stringPreferencesKey("authentication_type")
    }


    val preferenceDataFlow = myPreferenceDataStore.data
        .catch {exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            }
            else{
                throw exception
            }
        }
        .map{ preference->
            val name = preference[PreferenceKeys.NAME] ?: ""
            val apartmentName = preference[PreferenceKeys.APARTMENT_NAME] ?: ""
            val apartmentId = preference[PreferenceKeys.APARTMENT_ID] ?: ""
            val roomNumber = preference[PreferenceKeys.ROOM_NUMBER]
            val authenticationType = AuthenticationType.valueOf(
                preference[PreferenceKeys.AUTHENTICATION_TYPE] ?: AuthenticationType.UNAUTHENTICATED.name
            )

            PreferenceData(
                name = name,
                apartmentName = apartmentName,
                apartmentId = apartmentId,
                roomNumber = roomNumber,
                authenticationType = authenticationType
            )

        }

    suspend fun getApartmentName(): String {
        return preferenceDataFlow.map {
            it.apartmentName
        }.first()
    }

    suspend fun getApartmentId(): String {
        return preferenceDataFlow.map {
            it.apartmentId
        }.first()
    }

    suspend fun onSend(name: String , apartmentId: String , apartmentName: String){
            myPreferenceDataStore.edit {
                it[PreferenceKeys.NAME] = name
                it[PreferenceKeys.APARTMENT_NAME] = apartmentName
                it[PreferenceKeys.APARTMENT_ID] = apartmentId
            }
    }

    suspend fun onSendAuthenticationType(authenticationType: String){
        myPreferenceDataStore.edit {
            it[PreferenceKeys.AUTHENTICATION_TYPE] = authenticationType
        }
    }

    suspend fun onSendRoomNumber(roomNumber: String){
        myPreferenceDataStore.edit {
            it[PreferenceKeys.ROOM_NUMBER] = roomNumber
        }
    }

    suspend fun onClear(){
        myPreferenceDataStore.edit {
            it.clear()
        }
    }

}