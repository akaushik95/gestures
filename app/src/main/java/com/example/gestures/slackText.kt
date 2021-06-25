package com.example.gestures
import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toUri
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import java.lang.Object
import org.json.JSONObject
import java.util.*
import android.content.Context
import android.net.Uri
import com.android.volley.VolleyLog
import org.json.JSONException
import java.io.*
class slackText :AppCompatActivity(){
    companion object {
        fun test(dataModel: BugDataModel, activity: Activity) {

            val queue = Volley.newRequestQueue(activity)

            val url = "https://webhook.site/03384d56-1248-49ef-a21b-6fc2907cc152" // add slack api
            val country = dataModel.country
            val summary = dataModel.summary
            val desc = dataModel.description
            val select = dataModel.selectType
            val fixing = dataModel.fixingPriority
            val platform = dataModel.platform
            val output = String.format(
                """[{"type":"header","text":{"type":"plain_text","text":"Bug Reporter :firecracker: ","emoji":true}},
                    {"type":"section","fields":[{"type":"mrkdwn","text":"*Country*
                     %s "}]},{"type":"section","text":{"type":"mrkdwn","text":"*Summary*
                     %s "}},{"type":"section","text":{"type":"mrkdwn","text":"*Description*
                     %s "}},{"type":"section","text":{"type":"mrkdwn","text":"*Select Type*
                     %s "}},{"type":"section","text":{"type":"mrkdwn","text":"*Fixing Priority*
                     %s"}},{"type":"section","text":{"type":"mrkdwn","text":"*Platform*
                    %s"}}]""",
                country, summary, desc, select, fixing, platform
            )
            val params =  HashMap<String?, String?>()
            params["channel"] = "--"  // add channel here
            params["text"] = "Testing Hello World"
            params["username"] = "Testing Bot"
            params["blocks"] = output

            var timestamp : String? =null;
            val getrequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                url,
                JSONObject(params as Map<*, *>),
                Response.Listener { response -> timestamp = response.toString()},
                Response.ErrorListener { error -> Log.d("Error Response", error.toString()) }) {
//                              @Throws(AuthFailureError::class)
//                override fun getHeaders(): Map<String, String> {
//                    val headers = HashMap<String, String>()
//                    headers["Content-Type"] = "application/json"
//                    headers["Authorization"] =
//                        "--"
//                    return headers
//                }
                // uncomment above code for slack api
            }


            queue.add(getrequest)
            timestamp?.let { Log.d("response", it) };


            //##################################

            val upload_URL = "https://webhook.site/03384d56-1248-49ef-a21b-6fc2907cc152" // change to slack API for upload
            var jsonObject: JSONObject? = null
            var filePath: String? = null
            var byteArray : ByteArray? = null

            val entity: MultipartEntityBuilder = MultipartEntityBuilder.create()

            filePath = dataModel.filePath

            val contentURI = Uri.fromFile(File(filePath))

            fun buildMultipartEntity(file: File) {
                entity.addPart("file", FileBody(file))
                try {
                    entity.addPart("channel", StringBody("Channel_CODE"))
                } catch (e: UnsupportedEncodingException) {
                    VolleyLog.e("UnsupportedEncodingException")
                }
            }
            fun uploadImage(bitmap: Bitmap) {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                byteArray = stream.toByteArray()
                try {
                    jsonObject = JSONObject()
                    val imgname = Calendar.getInstance().timeInMillis.toString()
                    jsonObject!!.put("name", imgname)
                    jsonObject!!.put("channels", "rubbish")
                } catch (e: JSONException) {
                    Log.e("JSONObject Here", e.toString())
                }
                val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
                    Method.POST,
                    upload_URL,
                    null,
                    Response.Listener { jsonObject ->
                        Log.d("Response for Upload Image", jsonObject.toString())
                        queue!!.cache.clear()
                    },
                    Response.ErrorListener {
                            volleyError -> Log.e("Error in HTTP", volleyError.toString()) }) {
                    //            @Throws(AuthFailureError::class)
//            override fun getHeaders(): HashMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Authorization"] =
//                    "-"
//                return headers

                    // uncomment to add headers for slack API
//            }
                    override fun getBodyContentType(): String {
                        return entity.build().contentType.value
                    }
                    override fun getBody(): ByteArray {
                        val bos = ByteArrayOutputStream()
                        try {
                            entity.build().writeTo(bos)
                        } catch (e: IOException) {
                            VolleyLog.e("IOException writing to ByteArrayOutputStream")
                        }
                        return bos.toByteArray()
                    }
                }
                queue.add(jsonObjectRequest)
            }


            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, contentURI)
                var file2 = File(activity.baseContext.cacheDir, "Test")
                file2.createNewFile()
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val fos = FileOutputStream(file2)
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                buildMultipartEntity(file2)
                uploadImage(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}