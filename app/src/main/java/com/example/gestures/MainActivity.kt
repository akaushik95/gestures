package com.example.gestures

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.GestureDetectorCompat
import androidx.core.view.MotionEventCompat
import com.google.gson.Gson
import io.realm.Realm

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_input.*

class MainActivity : AppCompatActivity(),View.OnClickListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private lateinit var mDetector: GestureDetectorCompat
    var realm: Realm? = null
    val dataModel = BugDataModel()
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDetector = GestureDetectorCompat(this, this)
        mDetector.setOnDoubleTapListener(this)
        realm = Realm.getDefaultInstance()
        btn_submitData.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_fetchHistory.setOnClickListener(this)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }
    
    override fun onDown(event: MotionEvent): Boolean {
        Log.d("DEBUG_TAG", "onDown: $event")
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d("DEBUG_TAG", "onFling: $event1 $event2")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d("DEBUG_TAG", "onLongPress: $event")

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
            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }
        //performing negative action
        builder.setNegativeButton("Video"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Recording video",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d("DEBUG_TAG", "onScroll: $event1 $event2")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d("DEBUG_TAG", "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d("DEBUG_TAG", "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        Log.d("DEBUG_TAG", "onDoubleTap: $event")
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        Log.d("DEBUG_TAG", "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        Log.d("DEBUG_TAG", "onSingleTapConfirmed: $event")
        return true
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_submitData -> {
                addData()
            }

            R.id.btn_fetchHistory -> {
                fetchData()
            }

            R.id.btn_cancel -> {
                cancelAction()
            }

        }
    }


    fun addData() {

        try {

            dataModel.country = edt_country.text.toString()
            dataModel.summary = edt_summary.text.toString()
            dataModel.description = edt_description.text.toString()
            dataModel.selectType = edt_selectType.text.toString()
            dataModel.fixingPriority = edt_fixingPriority.text.toString()
            dataModel.platform = edt_platform.text.toString()

            realm!!.executeTransaction { realm -> realm.copyToRealm(dataModel) }

            Log.d("Status","dataModel in submit "+dataModel.toString())

            val jsonData = gson.toJson(dataModel)
            Toast.makeText(applicationContext,jsonData,Toast.LENGTH_LONG).show()


            Log.d("Status","Data Inserted !!!")
            clearFields()

        }catch (e:Exception){
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun clearFields(){

        edt_country.setText("")
        edt_summary.setText("")
        edt_description.setText("")
        edt_selectType.setText("")
        edt_fixingPriority.setText("")
        edt_platform.setText("")
    }

    fun cancelAction(){
        Log.d("Status"," Canceled!!!")
    }

    fun fetchData(){
        Log.d("Status","Inside fetchData")
        try {
            Log.d("Status","Inside fetchData")
            val dataModels: List<BugDataModel> =
                realm!!.where(BugDataModel::class.java).findAll()

            var arrayList = ArrayList<Any>()
//            arrayList.add("History")

//            val bugsArray = arrayOf("bug1","bug2","bug3")
            for (i in dataModels.size-1 downTo 0) {
                Log.d("Status",dataModels[i]
                    .toString())
                arrayList.add(dataModels[i])     //gson.toJson(item)

            }
            val arrayAdapter : ArrayAdapter<*>
            var bugsHistory = findViewById<ListView>(R.id.listview_history)

            arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, arrayList)
            bugsHistory.adapter = arrayAdapter

            Log.d("Status","Data Fetched !!!")

        } catch (e: Exception) {
            Log.d("Status","Something went Wrong !!!")
        }

    }
}

