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
            Log.d("test","inside slackText")
            val queue = Volley.newRequestQueue(activity)
            Log.d("test","after queue")
            //val url = "https://slack.com/api/chat.postMessage"
            val url = "https://webhook.site/03384d56-1248-49ef-a21b-6fc2907cc152"
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
            params["channel"] = "CFD5QKJE9"
            params["text"] = "Testing Hello World"
            params["thread_ts"] = "1624514105.083900"
            params["username"] = "Testing Bot"
            var timestamp : String? =null;
            val getrequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                url,
                JSONObject(params as Map<*, *>),
                Response.Listener { response -> timestamp = response.toString()},
                Response.ErrorListener { error -> Log.d("Error Response", error.toString()) }) {
                //              @Throws(AuthFailureError::class)
//                override fun getHeaders(): Map<String, String> {
//                    val headers = HashMap<String, String>()
//                    headers["Content-Type"] = "application/json"
//                    headers["Authorization"] =
//                        "Bearer xoxb-3157934939-422012316773-neP8V7MnZmMvBOebHzBqge5M"
//                    return headers
//                }
            }
            Log.d("test","before queue");
            queue.add(getrequest)
            timestamp?.let { Log.d("response", it) };
            //##################################
            var btn: AppCompatButton? = null
            var imageView: AppCompatImageView? = null
            val GALLERY = 1
            val upload_URL = "https://webhook.site/03384d56-1248-49ef-a21b-6fc2907cc152"
            var jsonObject: JSONObject? = null
//            var rQueue: RequestQueue? = null
            var filePath: String? = null
            var byteArray : ByteArray? = null
            val entity: MultipartEntityBuilder = MultipartEntityBuilder.create()

            filePath = dataModel.filePath
            val contentURI = Uri.fromFile(File(filePath))
//            val contentURI = "content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FWhatsApp%2FMedia%2FWhatsApp%20Documents%2FxS4CV9Sc_400x400.jpeg".toUri()
            Log.d("contentURI",contentURI.toString())
            fun buildMultipartEntity(file: File) {
                entity.addPart("file", FileBody(file))
                try {
                    entity.addPart("channel", StringBody("uiyuyhghj"))
                } catch (e: UnsupportedEncodingException) {
                    VolleyLog.e("UnsupportedEncodingException")
                }
            }
            fun uploadImage(bitmap: Bitmap) {
                Log.d("inUpload","herere in upload")
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                byteArray = stream.toByteArray()
                try {
                    jsonObject = JSONObject()
                    val imgname = Calendar.getInstance().timeInMillis.toString()
                    jsonObject!!.put("name", imgname)
                    //  Log.e("Image name", etxtUpload.getText().toString().trim());
//            jsonObject!!.put("file", encodedImage)
                    jsonObject!!.put("channels", "rubbish")
                    // jsonObject.put("aa", "aa");
                } catch (e: JSONException) {
                    Log.e("JSONObject Here", e.toString())
                }
                val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
                    Method.POST,
                    upload_URL,
                    null,
                    Response.Listener { jsonObject ->
                        Log.d("bbbbbb", jsonObject.toString())
                        queue!!.cache.clear()
                    },
                    Response.ErrorListener {
                            volleyError -> Log.e("aaaaaaa", volleyError.toString()) }) {
                    //            @Throws(AuthFailureError::class)
//            override fun getHeaders(): HashMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Authorization"] =
//                    "-"
//                return headers
//            }
//            override fun getParams(): MutableMap<String, String> {
//                return super.getParams()
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
//                val bos = ByteArrayOutputStream()
//                try {
//                    entity.writeTo(bos)
//                } catch (e: IOException) {
//                    VolleyLog.e("IOException writing to ByteArrayOutputStream")
//                }
//                return bos.toByteArray()
//               return byteArray!!
                    }
                }
                Log.d("imageQueue","before image queue")
                queue.add(jsonObjectRequest)
            }
            try {
                Log.d("try 189","before media")
                val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, contentURI)
                Log.d("try 191","eue")
                var file2 = File(activity.baseContext.cacheDir, "Test")
                file2.createNewFile()
                Log.d("try 194","eudfdfe")
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val fos = FileOutputStream(file2)
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                Log.d("try 203","eue")
                buildMultipartEntity(file2)
                Log.d("try 205","eue")
//                imageView!!.setImageBitmap(bitmap)
                Log.d("179","pass")
                uploadImage(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                //Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}