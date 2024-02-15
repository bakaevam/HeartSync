package com.heartsync.core.tools.navigation

enum class Route(
    val key: String,
) {
    START("start"),
    WELCOME("welcome"),
    SIGN_UP("sign_up/{scenario}"),
    ENTER_PHONE("enter_phone"),
    ENTER_EMAIL("enter_email/{scenario}"),
    SMS_CODE("sms_code"),
    PROFILE_DETAIL("profile_detail"),
    DISCOVERY("discovery"),
    MATCHES("matches"),
    MESSAGES("messages"),
    CABINET("cabinet"),
    CAMERA("camera"),
}