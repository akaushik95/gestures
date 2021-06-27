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

            val textMessage =
                String.format(
                    ":firecracker: *Bug Reporter:* @admin\\n*`Country`*%s" +
                            "*`Summary`* %s" +
                            "*`Description`* %s\\n*`Select Type`*Customer" +
                            " *`Fixing Priority`*P0-Funnel_Breaking" +
                            " *`Platform`*Android" +
                            " *`App Version`*7.3.21" +
                            " *`Customer Request ID`*-" +
                            " *`Provider Id`* -" +
                            " *`Customer Id`* 601234526271888",
                    apiFormData.country,
                    apiFormData.summary,
                    apiFormData.description
                )

            val jsonObject = JSONObject()
            jsonObject.put("channel", "xyz")
            jsonObject.put("text", textMessage)
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
                    // uploadAttachment(apiFormData.file,"TimeStamp",apiData)
                    uploadAttachment(apiFormData.file, "TimeStamp")
                }
            })
        }

        fun uploadAttachment(file: File, ts: String) {
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
                    // call UploadApiData Function
                    // uploadApiData(apiData,ts)
                }
            })

        }

        fun uploadApiData(apiData : ApiDataModel,ts : String){
            val client = OkHttpClient()
            val uploadBugUrl = "https://webhook.site/f29bf86d-6952-4127-8e30-c421126147ee"

            val textMessage =
                String.format(
                    ":firecracker: *Bug Reporter:* @admin\\n*`API URL`*%s" +
                            "*`API Request`* %s" +
                            "*`API Response`* %s",
                    apiData.apiUrl,
                    apiData.apiRequest,
                    apiData.apiResponse
                )

            val jsonObject = JSONObject()
            jsonObject.put("channel", "xyz")
            jsonObject.put("text", textMessage)
            jsonObject.put("ts",ts)
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
                }
            })
        }
    }
}