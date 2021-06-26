package com.example.gestures.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gestures.R

import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : BaseActivity(), View.OnClickListener {

    val TAG3: String = "MainActivity3"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        btn_goToM4.setOnClickListener(this)
        callAPIs(TAG3)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_goToM4 -> {
                //onBackPressed()
                val intent = Intent(this, MainActivity4::class.java)
                startActivity(intent)
            }

        }
    }
}