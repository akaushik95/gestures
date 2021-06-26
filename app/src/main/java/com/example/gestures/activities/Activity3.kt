package com.example.gestures.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.gestures.R

import kotlinx.android.synthetic.main.activity_main3.*

class Activity3 : BaseActivity(), View.OnClickListener {

    val TAG3: String = "Activity3"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        btn_goToM4.setOnClickListener(this)
        makeDummyApiCall(TAG3)
    }

    override fun getApiHistoryListView(): ListView? {
        return null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_goToM4 -> {
                val intent = Intent(this, Activity4::class.java)
                startActivity(intent)
            }

        }
    }
}