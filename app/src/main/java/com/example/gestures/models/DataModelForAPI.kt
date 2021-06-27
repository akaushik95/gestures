package com.example.gestures

import io.realm.RealmObject
import java.util.*

open class ApiDataModel : RealmObject() {
    var apiUrl: String = ""
    var apiRequest: String = ""
    var apiResponse: String = ""
    var createdAt: Long = 0
}