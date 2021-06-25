package com.example.gestures

import io.realm.RealmObject

open class ApiDataModel : RealmObject() {
    var apiUrl: String? = ""
    var apiRequest: String? = ""
    var apiResponse: String? = ""
}