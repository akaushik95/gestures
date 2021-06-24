package com.example.gestures

import io.realm.RealmObject

open class BugDataModel : RealmObject() {
    var country: String? = null
    var summary: String? = null
    var description: String? = null
    var selectType: String? = null
    var fixingPriority: String? = null
    var platform: String? = null
    var appVersion: String? = null
    var customerRequestId: String? = null
    var providerId: String? = null
    var customerId: String? = null
}