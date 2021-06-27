package com.example.gestures

import android.util.Log
import com.example.gestures.models.ApiFormData
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.IOException

class SendFile {

    companion object {
        val token = "-"
        val TAG = "SEND_FILE"

        fun uploadText(apiFormData: ApiFormData, apiDataModel: ApiDataModel?) {
            val client = OkHttpClient()
            val uploadBugUrl = "https://slack.com/api/chat.postMessage"

            val textMessage =
                String.format(
                    ":firecracker: *Bug Reporter:* @admin\n*`Country`*India" +
                            "*`Summary`* %s" +
                            "*`Description`* %s\n*`Select Type`*Customer" +
                            " *`Fixing Priority`*P0-Funnel_Breaking" +
                            " *`Platform`*Android" +
                            " *`App Version`*7.3.21" +
                            " *`Customer Request ID`*-" +
                            " *`Provider Id`* -" +
                            " *`Customer Id`* 601234526271888",
                    apiFormData.summary,
                    apiFormData.description
                )

            val jsonObject = JSONObject()
            jsonObject.put("channel", "CFD5QKJE9")
            jsonObject.put("text", textMessage)
            jsonObject.put("as_user", false)
            jsonObject.put("username", "Bug Reporter")
            jsonObject.put("icon_emoji", ":male-detective:")
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
                    val threadTimestamp = JSONObject(ok).getString("ts")
                    Log.d(TAG, ok.toString())
                    // need to send thread_ts in case of slack_api
                    // uploadAttachment(apiFormData.file,"TimeStamp",apiData)
                    uploadAttachment(apiFormData.file, threadTimestamp, apiDataModel)
                }
            })
        }

        fun uploadAttachment(file: File, ts: String, apiDataModel: ApiDataModel?) {
            val uploadAttachmentUrl = "https://slack.com/api/files.upload"
            val MEDIA_TYPE = MediaType.parse("application/octet-stream")

//            val jsonObject = JSONObject()
//            jsonObject.put("as_user", false)
//            jsonObject.put("username", "Admin")
//            jsonObject.put("icon_emoji", ":male_vampire:")
//            jsonObject.put("thread_ts", ts)
//            val jsonBodyRequest = RequestBody.create(
//                MediaType.parse("application/json"),
//                jsonObject.toString()
//            )

            val req: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    file.name,
                    RequestBody.create(MEDIA_TYPE, file)
                )
                .addFormDataPart("channels", "CFD5QKJE9")
//                .addPart(jsonBodyRequest)
                .addFormDataPart("thread_ts", ts).build()

            val request = Request.Builder()
                .url(uploadAttachmentUrl)
                .method("POST", req)
                .addHeader("Authorization", token)
                .build()

            val client = OkHttpClient()

            val response = client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, response.body().toString())
                    // call UploadApiData Function
//                    if (apiDataModel != null)
//                        uploadApiData(apiDataModel, ts)
                }
            })

        }

        fun uploadApiData(apiData: ApiDataModel, ts: String) {
            val client = OkHttpClient()
            val uploadBugUrl = "https://slack.com/api/chat.postMessage"

            val textMessage =
                String.format(
                    "*`API URL`* %s" +
                            "*`API Request`* %s" +
                            "*`API Response`* %s",
                    apiData.apiUrl,
                    apiData.apiRequest,
                    apiData.apiResponse
                )

            val jsonObject = JSONObject()
            jsonObject.put("channel", "CFD5QKJE9")
            jsonObject.put("text", textMessage)
            jsonObject.put("thread_ts", ts)
            jsonObject.put("as_user", false)
            jsonObject.put("username", "Admin")
            jsonObject.put("icon_emoji", ":male_vampire:")

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