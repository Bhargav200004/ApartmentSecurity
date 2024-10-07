package com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen

import android.graphics.Bitmap

data class SecurityGuardScreenData(
    val name: String = "",
    val phoneNumber: String = "",
    val vehicleNumber: String = "",
    val roomNumber : String = "",
    val reason: String = Reason.DELIVERY.value,
    val other : String = "",
    val apartmentName : String = "",
    val apartmentId : String = "",
    val pictureBitmap: Bitmap? = null,
    val reasonList : List<String> = getListOfReason(),
    val showModalBottomSheet : Boolean = false,
    val showDialog : Boolean = false,
){

    companion object{
        enum class Reason(val value: String) {
            DELIVERY("Delivery"),
            HOUSEKEEPER("HouseKeeper"),
            GUEST("Guest"),
            OTHER("Other")
        }

        fun getListOfReason(): List<String> {
            return listOf(
                "Devilry",
                "HouseKeeper",
                "Guest",
                "Other"
            )
        }

        fun getReason(value: String): Reason? {
            val map = Reason.entries.associateBy(Reason::value)
            return map[value]
        }
    }

}
