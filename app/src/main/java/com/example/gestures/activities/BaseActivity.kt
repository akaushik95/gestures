package com.example.gestures.activities

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseIntArray
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.Surface
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gestures.*
import com.example.gestures.fragments.FormFragment
import com.example.gestures.models.ApiFormData
import com.example.gestures.service.ForegroundService
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_input.*
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object LocalConstants {
    const val ssAction = "Screenshot"
    const val takingSs = "Taking Screenshot"
    const val cancelBtn = "Cancel"
    const val vidBtnStart = "Start Video"
    const val vidBtnStop = "Stop Video"
    const val vidProcessStart = "Screen Recording started"
    const val vidProcessStop = "Screen Recording stopped"
    const val nameDateFormat = "YYYY-MM-dd-hh-mm-ss-Ms"
}

abstract class BaseActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener, InteractionListenr {

    var realm: Realm? = null
    val dataModelGlobal = ApiDataModel()
    val MAX_DOCUMENTS_IN_DB = 5
    val TAG: String = "BaseActivity"

    private lateinit var mDetector: GestureDetectorCompat
    private var mScreenDensity = 0
    private var DISPLAY_WIDTH = 720
    private var DISPLAY_HEIGHT = 1280


    companion object {
        var dialog: ProgressDialog? = null
        var toggle: Boolean = false
        var mMediaProjection: MediaProjection? = null
        var mMediaProjectionCallback: MediaProjectionCallback? = null
        var mVirtualDisplay: VirtualDisplay? = null
        var mProjectionManager: MediaProjectionManager? = null
        var serviceIntent: Intent? = null
        var interactionListenr: InteractionListenr? = null

        private lateinit var filePath: String
        private const val REQUEST_CODE = 1000

        private val ORIENTATIONS = SparseIntArray()
        private const val REQUEST_PERMISSIONS = 10

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (dialog == null)
            dialog = ProgressDialog(this)
        if (interactionListenr == null)
            interactionListenr = this
        realm = Realm.getDefaultInstance()
        mDetector = GestureDetectorCompat(this, this)
        mDetector.setOnDoubleTapListener(this)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        mScreenDensity = metrics.densityDpi
        mProjectionManager = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(TAG, "onDown: $event")
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(TAG, "onFling: $event1 $event2")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(TAG, "onLongPress: $event")

        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("What would you like to do?")
        //set message for alert dialog
        builder.setMessage("")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing negative action
        if (toggle == false) {
            //performing positive action
            builder.setPositiveButton(LocalConstants.ssAction) { dialogInterface, which ->
                Toast.makeText(applicationContext, LocalConstants.takingSs, Toast.LENGTH_LONG)
                    .show()
                val rootView = window.decorView.findViewById<View>(android.R.id.content)
                val bitmap = getScreenShot(rootView)
                // if bitmap is not null then
                if (bitmap != null) {
                    saveBitmap(bitmap)
                    //call the form
                    val dataModels: List<ApiDataModel> = realm!!.where(ApiDataModel::class.java)
                        .sort("createdAt", Sort.ASCENDING)
                        .findAll()
                    var dialog = FormFragment.getNewInstance(filePath, dataModels.get(0))
                    dialog.show(supportFragmentManager, "formFragment")

                }
            }

            builder.setNegativeButton(LocalConstants.vidBtnStart) { dialogInterface, which ->
                Toast.makeText(
                    applicationContext,
                    LocalConstants.vidProcessStart,
                    Toast.LENGTH_LONG
                )
                    .show()
                toggle = true
                serviceIntent = Intent(this, ForegroundService::class.java)
                serviceIntent!!.putExtra(
                    Constants.foregroundNotifKey,
                    LocalConstants.vidProcessStart
                )
                ContextCompat.startForegroundService(applicationContext, serviceIntent!!)
                if (ContextCompat.checkSelfPermission(
                        this@BaseActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) + ContextCompat
                        .checkSelfPermission(
                            this@BaseActivity,
                            Manifest.permission.RECORD_AUDIO
                        )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this@BaseActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this@BaseActivity,
                            Manifest.permission.RECORD_AUDIO
                        )
                    ) {
                        toggle = false
                        Snackbar.make(
                            findViewById(android.R.id.content), R.string.label_permissions,
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction(
                            "ENABLE"
                        ) {
                            ActivityCompat.requestPermissions(
                                this@BaseActivity,
                                arrayOf(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO
                                ),
                                REQUEST_PERMISSIONS
                            )
                        }.show()
                    } else {
                        ActivityCompat.requestPermissions(
                            this@BaseActivity,
                            arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO
                            ),
                            REQUEST_PERMISSIONS
                        )
                    }
                } else {
                    onToggleScreenShare()
                }
            }
        } else {
            builder.setNegativeButton(LocalConstants.vidBtnStop) { dialogInterface, which ->
                Toast.makeText(applicationContext, LocalConstants.vidProcessStop, Toast.LENGTH_LONG)
                    .show()
                toggle = false
                onToggleScreenShare()
            }
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(true)
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            ?.setTextColor(resources.getColor(R.color.teal_700))
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            ?.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d(TAG, "onScroll: $event1 $event2")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(TAG, "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        Log.d(TAG, "onDoubleTap: $event")
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        Log.d(TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        Log.d(TAG, "onSingleTapConfirmed: $event")
        return true
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_CODE) {
            Log.e(TAG, "Unknown request code: $requestCode")
            return
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(
                this,
                "Screen Cast Permission Denied", Toast.LENGTH_SHORT
            ).show()
            toggle = false;
            return
        }
        mMediaProjectionCallback = MediaProjectionCallback()
        mMediaProjection = mProjectionManager!!.getMediaProjection(resultCode, data!!)
        with(mMediaProjection) { this?.registerCallback(mMediaProjectionCallback, null) }
        mVirtualDisplay = createVirtualDisplay()
        AppMediaRecorder.fetchMediaRecorder().start()
    }

    fun onToggleScreenShare() {
        if (toggle == true) {
            initRecorder()
            shareScreen()
        } else {
            stopService(serviceIntent)
            AppMediaRecorder.fetchMediaRecorder().stop()
            AppMediaRecorder.fetchMediaRecorder().reset()
            val dataModels: List<ApiDataModel> = realm!!.where(ApiDataModel::class.java)
                .sort("createdAt", Sort.ASCENDING)
                .findAll()
            var dialog = FormFragment.getNewInstance(filePath, dataModels.get(0))
            dialog.show(supportFragmentManager, "formFragment")
            Log.v(TAG, LocalConstants.vidProcessStop)
            stopScreenSharing()
        }
    }

    private fun setScreenMetrics() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        mScreenDensity = metrics.densityDpi
        DISPLAY_WIDTH = metrics.widthPixels
        DISPLAY_HEIGHT = metrics.heightPixels
    }

    private fun shareScreen() {
        startActivityForResult(mProjectionManager!!.createScreenCaptureIntent(), REQUEST_CODE)
    }

    private fun createVirtualDisplay(): VirtualDisplay {
        return mMediaProjection!!.createVirtualDisplay(
            "BaseActivity",
            DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            AppMediaRecorder.fetchMediaRecorder().surface, null /*Callbacks*/, null /*Handler*/
        )
    }


    private fun initRecorder() {
        try {
            AppMediaRecorder.fetchMediaRecorder().reset()
            val date = Date()
            val dateFormat = SimpleDateFormat(LocalConstants.nameDateFormat)
            dateFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
            val fileName = "/VID-" + dateFormat.format(date) + ".mp4"
            val directory: File = applicationContext.getDir("recordings", MODE_PRIVATE)
            val file = File(directory, fileName)
            filePath = file.absolutePath
            Log.d(TAG + " DEBUG-VideoName", filePath)
            AppMediaRecorder.fetchMediaRecorder().setAudioSource(MediaRecorder.AudioSource.MIC)
            AppMediaRecorder.fetchMediaRecorder().setVideoSource(MediaRecorder.VideoSource.SURFACE)
            AppMediaRecorder.fetchMediaRecorder()
                .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            AppMediaRecorder.fetchMediaRecorder().setOutputFile(filePath)
            AppMediaRecorder.fetchMediaRecorder().setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
            AppMediaRecorder.fetchMediaRecorder().setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            AppMediaRecorder.fetchMediaRecorder().setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            AppMediaRecorder.fetchMediaRecorder().setVideoEncodingBitRate(512 * 1000)
            AppMediaRecorder.fetchMediaRecorder().setVideoFrameRate(30)
            val rotation = windowManager.defaultDisplay.rotation
            val orientation = ORIENTATIONS[rotation + 90]
            AppMediaRecorder.fetchMediaRecorder().setOrientationHint(orientation)
            AppMediaRecorder.fetchMediaRecorder().prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    inner class MediaProjectionCallback : MediaProjection.Callback() {
        override fun onStop() {
            if (toggle == true) {
                toggle = false
                AppMediaRecorder.fetchMediaRecorder().stop()
                AppMediaRecorder.fetchMediaRecorder().reset()
                Log.v(TAG, LocalConstants.vidProcessStop)
            }
            mMediaProjection = null
            stopScreenSharing()
        }
    }


    private fun stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return
        }
        stopService(serviceIntent)
        mVirtualDisplay.let {
            it?.release()
        }
        destroyMediaProjection()
    }


    public override fun onDestroy() {
        super.onDestroy()
    }


    private fun destroyMediaProjection() {
        mMediaProjection.let {
            if (mMediaProjectionCallback != null)
                it?.unregisterCallback(mMediaProjectionCallback)
            it?.stop()
        }
        Log.i(TAG, "MediaProjection Stopped")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                if (grantResults.size > 0 && grantResults[0] +
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    onToggleScreenShare()
                } else {
                    toggle = false
                    Snackbar.make(
                        findViewById(android.R.id.content), R.string.label_permissions,
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(
                        "ENABLE"
                    ) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        intent.data = Uri.parse("package:$packageName")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        startActivity(intent)
                    }.show()
                }
                return
            }
        }
    }

    private fun getScreenShot(view: View): Bitmap? {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap) {
        // Generating a file name
        val date = Date()
        val dateFormat = SimpleDateFormat(LocalConstants.nameDateFormat)
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        val fileName = "SS-" + dateFormat.format(date) + ".jpg"
        val directory: File = applicationContext.getDir("screenshots", MODE_PRIVATE)
        val file = File(directory, fileName)
        filePath = file.absolutePath
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d(TAG + " DEBUG-ScreenshotName", filePath)
    }

    fun manageDB() {
        val dataModels: List<ApiDataModel> = realm!!.where(ApiDataModel::class.java)
            .sort("createdAt", Sort.ASCENDING)
            .findAll()

        //delete from 0 index
        Log.d(TAG, "size of db ${dataModels.size}")
        if (dataModels.size > MAX_DOCUMENTS_IN_DB) {
            Log.d(TAG, "objects greater than $MAX_DOCUMENTS_IN_DB")

            realm!!.executeTransaction {

                dataModels[0].deleteFromRealm()

            }

        }

    }

    fun addToDB(apiUrl: String, req: String, res: String) {
        Log.d(TAG, "inside addToDB $apiUrl")
        try {

            dataModelGlobal.apiUrl = apiUrl
            dataModelGlobal.apiRequest = req
            dataModelGlobal.apiResponse = res
            dataModelGlobal.createdAt = System.currentTimeMillis()

            realm!!.executeTransaction { realm -> realm.copyToRealm(dataModelGlobal) }


            Log.d(TAG, "Api Data Inserted in DB!!! $apiUrl")

            manageDB()
        } catch (e: Exception) {
            Log.d(TAG, "Something went Wrong !!!")
        }
    }

    fun fetchHistoryOfApis() {
        try {
            val apiHistoryListView = getApiHistoryListView()
            apiHistoryListView.let {
                val dataModels: List<ApiDataModel> = realm!!.where(ApiDataModel::class.java)
                    .sort("createdAt", Sort.ASCENDING)
                    .findAll()

                var arrayList = ArrayList<Any>()
                for (i in dataModels.size - 1 downTo 0) {
                    Log.d(TAG, "index is $i")
                    Log.d(
                        TAG, dataModels[i]
                            .toString()
                    )
                    arrayList.add(dataModels[i])     //gson.toJson(item)
                }
                val arrayAdapter: ArrayAdapter<*>
                arrayAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1, arrayList
                )
                apiHistoryListView?.adapter = arrayAdapter
                Log.d(TAG, "Data Fetched for APIs !!!")
            }
        } catch (e: Exception) {
            Log.d(TAG, getString(R.string.error_occured))
        }
    }

    fun makeDummyApiCall(name: String) {
        Log.d(TAG, "api call $name")
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.agify.io/?name=$name"
        var finalResponse: JSONObject

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                finalResponse = JSONObject(response.toString())

                addToDB(url, "", finalResponse.toString())
            },
            Response.ErrorListener { Log.d(name, "That didn't work!") })

        queue.add(stringRequest)
    }

    fun uploadErrorLog(apiFormData: ApiFormData, apiDataModel: ApiDataModel?) {
        SendFile.uploadText(apiFormData, apiDataModel, this)
        showProgressDialog()
    }

    override fun showProgressDialog() {
        dialog?.setMessage("Uploading bug")
        dialog?.show()
    }

    override fun dismissProgressDialog() {
        dialog?.dismiss()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    abstract fun getApiHistoryListView(): ListView?

}

interface InteractionListenr {
    fun showProgressDialog()
    fun dismissProgressDialog()
    fun showToast(message: String)
}