package com.example.apartmentsecurity.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreen{
    @Serializable
    data object MainScreen : AppScreen()
}

@Serializable
sealed class AuthScreen{
    @Serializable
    data object AdminAuth : AuthScreen()
    @Serializable
    data object UserAuth : AuthScreen()
    @Serializable
    data object SecurityAuth : AuthScreen()
}

@Serializable
sealed class AdminAuthScreen{
    @Serializable
    data object Signin : AdminAuthScreen()
    @Serializable
    data object Signup : AdminAuthScreen()
}

@Serializable
sealed class UserAuthScreen{
    @Serializable
    data object Signin : UserAuthScreen()
    @Serializable
    data object Signup : UserAuthScreen()
}

@Serializable
sealed class SecurityAuthScreen{
    @Serializable
    data object Signin : SecurityAuthScreen()
    @Serializable
    data object Signup : SecurityAuthScreen()
}

@Serializable
sealed class AdminScreen{
    @Serializable
    data object Admin : AdminScreen()
}

@Serializable
sealed class UserScreen{
    @Serializable
    data object Admin : UserScreen()
}

@Serializable
sealed class SecurityScreen{
    @Serializable
    data object Admin : SecurityScreen()
}