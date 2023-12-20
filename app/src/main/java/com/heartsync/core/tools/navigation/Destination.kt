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
}
