package com.heartsync.features.welcome.data.repositories

import com.heartsync.R
import com.heartsync.core.network.store.FirebaseStore
import com.heartsync.core.providers.TextProvider
import com.heartsync.features.welcome.data.models.DbPhoto
import com.heartsync.features.welcome.domain.models.WelcomePage
import com.heartsync.features.welcome.domain.repositories.WelcomeRepository

class WelcomeRepositoryImpl(
    private val database: FirebaseStore,
    private val textProvider: TextProvider,
) : WelcomeRepository {

    override suspend fun getWelcomePages(): List<WelcomePage> {
        val photos = database.getWelcomePhotos()
            .map(DbPhoto::url)
            .take(PAGES_COUNT)
        val pageTitles = textProvider.getStringArray(R.array.welcome_pages_titles)
        val pagesDescriptions = textProvider.getStringArray(R.array.welcome_pages_descriptions)
        return photos.mapIndexed { index, url ->
            WelcomePage(
                url = url,
                title = pageTitles[index],
                description = pagesDescriptions[index],
            )
        }
    }

    private companion object {

        private const val PAGES_COUNT = 3
    }
}