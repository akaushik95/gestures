package com.example.gestures.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestures.R

class MainActivity4 : BaseActivity() {

    val TAG4: String = "MainActivity4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        callAPIs(TAG4)
    }
}