package com.heartsync.core.tools.navigation

object DestinationMapper {

    fun appendParams(
        oldParams: String,
        vararg newParams: Pair<String, Any?>,
    ): String {
        val builder = StringBuilder(oldParams)

        newParams.forEach {
            it.second?.toString()?.let { arg ->
                builder.append("/$arg")
            }
        }

        return builder.toString()
    }
}