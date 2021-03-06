package com.example.gestures

import io.realm.RealmObject

open class BugDataModel : RealmObject() {
    var country: String? = ""
    var summary: String? = ""
    var description: String? = ""
    var selectType: String? = ""
    var fixingPriority: String? = ""
    var platform: String? = ""
    var appVersion: String? = ""
    var customerRequestId: String? = ""
    var providerId: String? = ""
    var customerId: String? = ""
    var filePath: String? = ""
}