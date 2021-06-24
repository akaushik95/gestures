package com.example.gestures

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Log.d("DEBUG_TAG","toggle in mainactivity2: $toggle")
        btn_goToM1.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_goToM1 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}