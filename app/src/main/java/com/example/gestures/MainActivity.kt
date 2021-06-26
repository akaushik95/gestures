package com.example.gestures

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.form_fragment_dialog.view.*
import org.json.JSONObject

class MainActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_goToM2.setOnClickListener(this)
        fab.setOnClickListener(this)
        btn_fetchHistoryofApi.setOnClickListener(this)
        Log.d("DEBUG_TAG","in oncreate before call api")
        callAPIs()
        Log.d("DEBUG_TAG","in oncreate after call api")
    }

    fun callAPIs(){
        Log.d("DEBUG_TAG","inside call apis")
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.agify.io/?name=professorInM1"
        var finalResponse : JSONObject

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                finalResponse = JSONObject(response.toString())
                Log.d("DEBUG_TAG",finalResponse.toString())
                addToDB(url,"",finalResponse.toString())
            },
            Response.ErrorListener { Log.d("DEBUG_TAG","That didn't work!") })

        queue.add(stringRequest)
    }

    fun fetchHistoryOfApis(){
        try {
            Log.d("DEBUG_TAG","Inside fetchData of API")
            val dataModels: List<ApiDataModel> =
                realm!!.where(ApiDataModel::class.java).findAll()

            var arrayList = ArrayList<Any>()
//            arrayList.add("History")

//            val bugsArray = arrayOf("bug1","bug2","bug3")
            for (i in dataModels.size-1 downTo 0) {
                Log.d("Status",dataModels[i]
                    .toString())
                arrayList.add(dataModels[i])     //gson.toJson(item)

            }
            val arrayAdapter : ArrayAdapter<*>

            val apiHistory = this.listview_history_of_api

            arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, arrayList)
            apiHistory.adapter = arrayAdapter

            Log.d("DEBUG_TAG","Data Fetched for APIs !!!")

        } catch (e: Exception) {
            Log.d("DEBUG_TAG","Something went Wrong in API !!!")
        }
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

            R.id.btn_fetchHistoryofApi -> {
                Log.d("DEBUG_TAG","FetchData of API Clicked")
                fetchHistoryOfApis()
            }
        }
    }
}
