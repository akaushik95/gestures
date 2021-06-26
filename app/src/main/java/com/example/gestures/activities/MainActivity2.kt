package com.example.gestures.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import com.example.gestures.R
import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONObject

class MainActivity2 : BaseActivity(),View.OnClickListener {

    val TAG2: String = "MainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_goToM3.setOnClickListener(this)
        callAPIs(TAG2)

    }


    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_goToM3 -> {

                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
            }

        }
    }


}