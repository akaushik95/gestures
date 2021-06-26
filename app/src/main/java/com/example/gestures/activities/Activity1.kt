package com.example.gestures.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.gestures.R
import kotlinx.android.synthetic.main.activity_main.*

class Activity1 : BaseActivity(), View.OnClickListener {
    val TAG1: String = "Activity1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_goToM2.setOnClickListener(this)
        btn_fetchHistoryofApi.setOnClickListener(this)
        makeDummyApiCall(TAG1)
    }

    override fun getApiHistoryListView(): ListView? {
        return listview_history_of_api
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_goToM2 -> {
                val intent = Intent(this, Activity2::class.java)
                startActivity(intent)
            }

            R.id.btn_fetchHistoryofApi -> {
                fetchHistoryOfApis()
            }
        }
    }
}
