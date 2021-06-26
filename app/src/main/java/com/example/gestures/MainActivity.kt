package com.example.gestures

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.util.*

class MainActivity : BaseActivity(),View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btn_goToM2.setOnClickListener(this)
        fab.setOnClickListener(this)

        callAPIs()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                val contentURI = data.data
//                //               val contentURI = "/sdk_gphone_arm64/Download/hello.png".toUri()
//                //               val contentURI = "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F21/ORIGINAL/NONE/image%2Fjpeg/1703742162".toUri()
//                // val contentURI = "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F15/ORIGINAL/NONE/image%2Fpng/1805752670".toUri()
//                //filePath = contentURI?.path
//
//
//                //Log.d("Filepath",filePath.toString())
//                try {
//
//                    Log.d("TAG",data.data.toString());
//
//                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
//                    var file2 = File(baseContext.cacheDir, "Test")
//                    file2.createNewFile()
//
//                    val bos = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
//                    val bitmapdata = bos.toByteArray()
//
//                    val fos = FileOutputStream(file2)
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//
//                    buildMultipartEntity(file2)
//
//                    //imageView!!.setImageBitmap(bitmap)
//                    uploadImage(bitmap)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

//    private fun buildMultipartEntity(file: File) {
//        entity.addPart("file", FileBody(file))
//        try {
//            entity.addPart("channel", StringBody("CFD5QKJE9"))
//        } catch (e: UnsupportedEncodingException) {
//            VolleyLog.e("UnsupportedEncodingException")
//        }
//    }

//    private fun uploadImage(bitmap: Bitmap) {
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        var byteArray : ByteArray? = null
//        byteArray = stream.toByteArray()
//        var jsonObject: JSONObject? = null
//        var rQueue: RequestQueue? = null
//        try {
//            jsonObject = JSONObject()
//            val imgname = Calendar.getInstance().timeInMillis.toString()
//            jsonObject!!.put("name", imgname)
//            jsonObject!!.put("channels", "CFD5QKJE9")
//        } catch (e: JSONException) {
//            Log.e("JSONObject Here", e.toString())
//        }
//        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
//            Method.POST,
//            upload_URL,
//            null,
//            Response.Listener { jsonObject ->
//                Log.e("aaaaaaa", jsonObject.toString())
//                rQueue!!.cache.clear()
//                Toast.makeText(application, "Image Uploaded Successfully", Toast.LENGTH_SHORT)
//                    .show()
//            },
//            Response.ErrorListener {
//                    volleyError -> Log.e("aaaaaaa", volleyError.toString()) }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): HashMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Authorization"] =
//                    "-"
//                return headers
//            }
//            override fun getBodyContentType(): String {
//                return entity.build().contentType.value
//            }
//            override fun getBody(): ByteArray {
//                val bos = ByteArrayOutputStream()
//                try {
//                    entity.build().writeTo(bos)
//                } catch (e: IOException) {
//                    VolleyLog.e("IOException writing to ByteArrayOutputStream")
//                }
//                return bos.toByteArray()
//            }
//        }
//        rQueue = Volley.newRequestQueue(this@MainActivity)
//        rQueue?.add(jsonObjectRequest)
//    }


    fun callAPIs()
    {

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

