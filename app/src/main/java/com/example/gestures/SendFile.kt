package com.example.gestures

import android.util.Log
import com.example.gestures.models.ApiFormData
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.IOException

class SendFile {

    companion object {
        val token = "bearer xoxbstuff"
        val TAG = "SEND_FILE"

        fun uploadText(apiFormData: ApiFormData) {
            val client = OkHttpClient()
            val uploadBugUrl = "https://webhook.site/f29bf86d-6952-4127-8e30-c421126147ee"

            val output = String.format(
                "[{\"type\":\"header\",\"text\":" +
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
                apiFormData.country,
                apiFormData.summary,
                apiFormData.description,
                apiFormData.type,
                apiFormData.fixingPriority,
                apiFormData.platform
            )

            val jsonObject = JSONObject()
            jsonObject.put("channel", "xyz")
            jsonObject.put("blocks", output)
            val jsonBody = RequestBody.create(
                MediaType.parse("application/json"),
                jsonObject.toString()
            )
            val request = Request.Builder()
                .url(uploadBugUrl)
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
                    Log.d(TAG, ok.toString())
                    // need to send thread_ts in case of slack_api
                    uploadAttachment(apiFormData.file, "TimeStamp")
                }
            })
        }

        fun uploadAttachment(file: File?, ts: String?) {
            val uploadAttachmentUrl = "https://webhook.site/f29bf86d-6952-4127-8e30-c421126147ee"
            val MEDIA_TYPE_PNG = MediaType.parse("application/octet-stream")
            val req: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    "UC BUG",
                    RequestBody.create(MEDIA_TYPE_PNG, file)
                )
                .addFormDataPart("channel", "XXXX")
                .addFormDataPart("ts", ts).build()

            val request = Request.Builder()
                .url(uploadAttachmentUrl)
                .post(req)
                .header("Authorization", token)
                .build()

            val client = OkHttpClient()

            val response = client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                }
            })

        }
    }
}