package com.example.gestures

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.net.UnknownHostException

class SendFile {

    companion object {
        val token = "bearer xoxbstuff"
        fun uploadtext(values: Array<String>, file: File){
            val client = OkHttpClient()
            val ok = ""
            // Things to be changed

            val url = "https://webhook.site/f29bf86d-6952-4127-8e30-c421126147ee"


            val output = String.format("[{\"type\":\"header\",\"text\":" +
                    "{\"type\":\"plain_text\",\"text\":\"Bug Reporter :firecracker: \",\"emoji\":true}}" +
                    ",{\"type\":\"section\",\"fields\":[{\"type\":\"mrkdwn\"," +
                    "\"text\":\"*Country*\n %s \"}]}," +
                    "{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\"," +
                    "\"text\":\"*Summary*\n %s \"}}," +
                    "{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\"," +
                    "\"text\":\"*Description*\n %s \"}}," +
                    "{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\"," +
                    "\"text\":\"*Select Type*\n %s \"}}," +
                    "{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\"," +
                    "\"text\":\"*Fixing Priority*\n %s  \"}}," +
                    "{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\"," +
                    "\"text\":\"*Platform*\n %s \"}}]",
            values[0], values[1], values[2], values[3], values[4], values[5])



            ////////////////////////////////////////////////////////////////////
            val jsonObject = JSONObject()
            try {
                jsonObject.put("channel", "xyz")
                jsonObject.put("blocks", output)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val jsonBody = RequestBody.create(
                MediaType.parse("application/json"),
                jsonObject.toString()
            )
            val request = Request.Builder()
                .url(url)
                .post(jsonBody)
                .header("Authorization", token)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val ok = response.body()?.string()
                    Log.d("response",ok.toString())
                    // need to send thread_ts in case of slack_api
                    upload(file,"TimeStamp")
                }
            })
        }

        fun upload(file: File? , ts : String?) {
            try {

                val url2 = "https://webhook.site/f29bf86d-6952-4127-8e30-c421126147ee"
                val MEDIA_TYPE_PNG = MediaType.parse("application/octet-stream")
                Log.d("2","1")
                val req: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        "UC BUG",
                        RequestBody.create(MEDIA_TYPE_PNG, file)
                    )
                    .addFormDataPart("channel" ,"XXXX")
                    .addFormDataPart("ts",ts).build()

                val request = Request.Builder()
                    .url(url2)
                    .post(req)
                    .header("Authorization", token)
                    .build()

                val client = OkHttpClient()

                val response =  client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                    }
                })

            } catch (e: UnknownHostException) {
                Log.e("Error", "Error: " + e.localizedMessage)
            } catch (e: UnsupportedEncodingException) {
                Log.e("Error", "Error: " + e.localizedMessage)
            } catch (e: Exception) {
                Log.e("Other Error", "Other Error: " + e.localizedMessage)
            }
        }
    }
}