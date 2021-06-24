package com.example.gestures

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_goToM2.setOnClickListener(this)
        fab.setOnClickListener(this)

        callAPIs()
    }

    fun callAPIs(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.agify.io/?name=bella"
        var finalResponse : JSONObject

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                finalResponse = JSONObject(response.toString())
                Log.d("DEBUG_TAG",finalResponse.toString())
            },
            Response.ErrorListener { Log.d("DEBUG_TAG","That didn't work!") })

        queue.add(stringRequest)
    }

    fun createDialog(){
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Select the option")
        //set message for alert dialog
        builder.setMessage("ss/video")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Screenshot"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Taking screenshot", Toast.LENGTH_LONG).show()
        }
        //performing cancel action
        builder.setNeutralButton("Cancel"){dialogInterface , which ->
            Toast.makeText(applicationContext,"clicked cancel\n operation cancel", Toast.LENGTH_LONG).show()
        }
        //performing negative action
        builder.setNegativeButton("Video"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Recording video", Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_goToM2 -> {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }

            R.id.fab -> {
                createDialog()
            }

        }
    }
    }

