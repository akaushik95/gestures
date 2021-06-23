package com.example.gestures

import io.realm.RealmObject

open class BugDataModel : RealmObject() {
    var country: String? = null
    var summary: String? = null
    var description: String? = null

}