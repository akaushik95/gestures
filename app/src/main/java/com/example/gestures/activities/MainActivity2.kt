package com.example.gestures.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gestures.R
import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONObject

class MainActivity2 : BaseActivity(),View.OnClickListener {

    val TAG2: String = "MainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Log.d(TAG2,"toggle in mainactivity2: $toggle")
        btn_goToM1.setOnClickListener(this)
        callAPIs()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_goToM1 -> {
                //onBackPressed()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    fun callAPIs(){
        Log.d(TAG2,"inside callapis")
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.agify.io/?name=professorInM2"
        var finalResponse : JSONObject

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                finalResponse = JSONObject(response.toString())
                Log.d(TAG2,finalResponse.toString())
                addToDB(url,"",finalResponse.toString())
            },
            Response.ErrorListener { Log.d(TAG2,"That didn't work!") })

        queue.add(stringRequest)
    }
}