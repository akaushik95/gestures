package com.example.gestures

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_goToM2.setOnClickListener(this)
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
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_goToM2 -> {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }

        }
    }
    }

