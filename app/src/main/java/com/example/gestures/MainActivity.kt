package com.example.gestures

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_input.*

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private lateinit var mDetector: GestureDetectorCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDetector = GestureDetectorCompat(this, this)
        mDetector.setOnDoubleTapListener(this)

        val fab: View = findViewById(R.id.fab)

        fab.setOnClickListener(View.OnClickListener {
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
        })


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
//        Log.d("DEBUG_TAG", "onDoubleTap: $event")
//        return true
        var dialog=FormFragment()
        dialog.show(supportFragmentManager,"formFragment")
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


}

