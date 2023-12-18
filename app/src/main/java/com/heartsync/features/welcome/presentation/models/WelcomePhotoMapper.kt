package com.heartsync.features.welcome.presentation.models

import com.heartsync.features.welcome.domain.models.WelcomePage

object WelcomePhotoMapper {

    fun toUiWelcomePages(
        photos: List<WelcomePage>,
    ): List<UiWelcomePage> =
        photos.map { page ->
            UiWelcomePage(
                photoUrl = page.url,
                title = page.title,
                description = page.description,
            )
        }

}