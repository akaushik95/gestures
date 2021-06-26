package com.example.gestures.activities

import android.os.Bundle
import android.widget.ListView
import com.example.gestures.R

class Activity4 : BaseActivity() {

    val TAG4: String = "Activity4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        makeDummyApiCall(TAG4)
    }

    override fun getApiHistoryListView(): ListView? {
        return null
    }
}