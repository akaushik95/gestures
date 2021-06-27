package com.example.gestures.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ApiResponseDataModel(
    var apiUrl: String? = null,
    var apiRequest: String? = null,
    var apiResponse: String? = null,
    var createdAt: Long = 0
) : Parcelable