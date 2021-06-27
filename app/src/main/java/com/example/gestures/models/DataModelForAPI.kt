package com.example.gestures

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize

@Parcelize
open class ApiDataModel : RealmObject(), Parcelable {
    var apiUrl: String = ""
    var apiRequest: String = ""
    var apiResponse: String = ""
    var createdAt: Long = 0
}