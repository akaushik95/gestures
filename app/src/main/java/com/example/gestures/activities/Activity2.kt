package com.example.gestures.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView

import com.example.gestures.R
import kotlinx.android.synthetic.main.activity_main2.*

class Activity2 : BaseActivity(), View.OnClickListener {

    val TAG2: String = "Activity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_goToM3.setOnClickListener(this)
        makeDummyApiCall(TAG2)

    }

    override fun getApiHistoryListView(): ListView? {
        return null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_goToM3 -> {
                val intent = Intent(this, Activity3::class.java)
                startActivity(intent)
            }
        }
    }


}