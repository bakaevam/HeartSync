package com.heartsync.core.tools.navigation

sealed class Destination(
    protected val route: String,
    vararg params: String,
) {

    val fullRoute: String =
        if (params.isEmpty()) {
            route
        } else {
            val builder = StringBuilder(route)
            params.forEach { builder.append("/{${it}}") }
            builder.toString()
        }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object WelcomeScreen : NoArgumentsDestination(Route.WELCOME.key)

    object SignUpScreen : NoArgumentsDestination(Route.SIGN_UP.key)

    object EnterPhoneScreen : NoArgumentsDestination(Route.ENTER_PHONE.key)

    object EnterEmailScreen : NoArgumentsDestination(Route.ENTER_EMAIL.key)

    object DiscoveryScreen : NoArgumentsDestination(Route.DISCOVERY.key)

    object SmsCodeScreen : Destination(Route.SMS_CODE.key, "phone") {
        const val KEY_PHONE = "phone"

        operator fun invoke(phone: String): String = route.appendParams(
            KEY_PHONE to phone,
        )
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}
