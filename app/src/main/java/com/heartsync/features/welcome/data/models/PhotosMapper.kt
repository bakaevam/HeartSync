package com.heartsync.features.welcome.data.models

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.heartsync.core.tools.EMPTY_STRING

object PhotosMapper {

    private const val FIELD_PHOTO_TYPE = "type"
    private const val FIELD_PHOTO_URL = "url"

    fun toDbPhoto(document: QueryDocumentSnapshot): DbPhoto =
        DbPhoto(
            url = document.getString(FIELD_PHOTO_URL) ?: EMPTY_STRING,
            type = document.getString(FIELD_PHOTO_TYPE) ?: EMPTY_STRING,
        )
}