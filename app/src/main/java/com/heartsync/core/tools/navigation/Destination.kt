package com.heartsync.core.tools.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.heartsync.features.welcome.domain.models.AuthScenario

sealed class Destination(
    protected val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
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

    object StartScreen : NoArgumentsDestination(Route.START.key)

    object WelcomeScreen : NoArgumentsDestination(Route.WELCOME.key)

    object SignUpScreen : Destination(
        route = Route.SIGN_UP.key,
        arguments = listOf(
            navArgument("scenario") {
                type = NavType.EnumType(AuthScenario::class.java)
            },
        )
    ) {
        const val KEY_SCENARIO = "scenario"

        operator fun invoke(scenario: AuthScenario): String = route.appendParams(
            KEY_SCENARIO to scenario,
        )
    }

    object EnterPhoneScreen : NoArgumentsDestination(Route.ENTER_PHONE.key)

    object EnterEmailScreen : Destination(
        route = Route.ENTER_EMAIL.key,
        arguments = listOf(
            navArgument("scenario") {
                type = NavType.EnumType(AuthScenario::class.java)
            },
        ),
    ) {

        const val KEY_SCENARIO = "scenario"

        operator fun invoke(scenario: AuthScenario): String = route.appendParams(
            KEY_SCENARIO to scenario,
        )
    }

    object ProfileDetailScreen : NoArgumentsDestination(Route.PROFILE_DETAIL.key)

    object DiscoveryScreen : NoArgumentsDestination(Route.DISCOVERY.key)

    object SmsCodeScreen : Destination(Route.SMS_CODE.key, params = arrayOf("phone")) {
        const val KEY_PHONE = "phone"

        operator fun invoke(phone: String): String = route.appendParams(
            KEY_PHONE to phone,
        )
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        if (!this.contains(it.first)) {
            it.second?.toString()?.let { arg ->
                builder.append("/$arg")
            }
        }
    }
    var str = builder.toString()
    params.forEach { param ->
        if (str.contains(param.first)) {
            str = str.replace('{' + param.first + '}', param.second.toString())
        }
    }

    return str
}
