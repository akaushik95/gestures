package com.example.gestures.activities

import android.Manifest
import android.app.AlertDialog
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gestures.ApiDataModel
import com.example.gestures.AppMediaRecorder
import com.example.gestures.service.ForegroundService
import com.example.gestures.fragments.FormFragment
import com.example.gestures.R
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_input.*
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import com.example.gestures.Constants

object LocalConstants{
    const val ssBtn = "Screenshot"
    const val ssProcess = "Taking Screenshot"
    const val cancelBtn = "Cancel"
    const val vidBtnStart = "Start Video"
    const val vidBtnStop = "Stop Video"
    const val vidProcessStart = "Screen Recording started"
    const val vidProcessStop = "Screen Recording stopped"
    const val nameDateFormat = "YYYY-MM-dd-hh-mm-ss-Ms"
}

abstract class BaseActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    var realm: Realm? = null
    val dataModelGlobal = ApiDataModel()
    val MAX_DOCUMENTS_IN_DB = 3
    val TAG: String = "BaseActivity"

    private lateinit var mDetector: GestureDetectorCompat
    private var mScreenDensity = 0
    private var DISPLAY_WIDTH = 720
    private var DISPLAY_HEIGHT = 1280
    private var mProjectionManager: MediaProjectionManager? = null
    private var mMediaProjection: MediaProjection? = null
    private var mVirtualDisplay: VirtualDisplay? = null
    private var mMediaProjectionCallback: MediaProjectionCallback? = null
    private var serviceIntent: Intent? = null

    companion object {
        var toggle: Boolean = false
        lateinit var filePath: String
        private const val TAG = "MainActivity"
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
        realm = Realm.getDefaultInstance()
        mDetector = GestureDetectorCompat(this, this)
        mDetector.setOnDoubleTapListener(this)
        serviceIntent = Intent(this, ForegroundService::class.java)
        serviceIntent!!.putExtra(Constants.foregroundNotifKey, Constants.foregroundNotifVal)
        ContextCompat.startForegroundService(this, serviceIntent!!)
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
        builder.setMessage("ss/video")
        builder.setIcon(android.R.drawable.ic_dialog_alert)


        //performing positive action
        builder.setPositiveButton(LocalConstants.ssBtn) { dialogInterface, which ->
            Toast.makeText(applicationContext, LocalConstants.ssProcess, Toast.LENGTH_LONG).show()
            val rootView = window.decorView.findViewById<View>(android.R.id.content)
            val bitmap = getScreenShot(rootView)
            // if bitmap is not null then
            if (bitmap != null) {
                saveBitmap(bitmap)
                //call the form
                var dialog = FormFragment.getNewInstance(filePath)
                dialog.show(supportFragmentManager, "formFragment")

            }
        }
        //performing cancel action
        builder.setNeutralButton(LocalConstants.cancelBtn) { dialogInterface, which ->
            Toast.makeText(
                applicationContext,
                "clicked cancel\n operation cancel",
                Toast.LENGTH_LONG
            ).show()
        }

        //performing negative action
        if (toggle == false) {
            builder.setNegativeButton(LocalConstants.vidBtnStart) { dialogInterface, which ->
                Toast.makeText(applicationContext, LocalConstants.vidProcessStart, Toast.LENGTH_LONG)
                    .show()
                toggle = true
                serviceIntent!!.putExtra(Constants.foregroundNotifKey, LocalConstants.vidProcessStart)
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
        alertDialog.setCancelable(false)
        alertDialog.show()
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
            var dialog = FormFragment.getNewInstance(filePath)
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
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager!!.createScreenCaptureIntent(), REQUEST_CODE)
            return
        }
        mVirtualDisplay = createVirtualDisplay()
        AppMediaRecorder.fetchMediaRecorder().start()
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
            AppMediaRecorder.fetchMediaRecorder().setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
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


    private inner class MediaProjectionCallback : MediaProjection.Callback() {
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
        mVirtualDisplay!!.release()
        destroyMediaProjection()
    }


    public override fun onDestroy() {
        super.onDestroy()
        destroyMediaProjection()
        toggle = false
    }


    private fun destroyMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection!!.unregisterCallback(mMediaProjectionCallback)
            mMediaProjection!!.stop()
            mMediaProjection!!
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
        val dataModels: List<ApiDataModel> =
            realm!!.where(ApiDataModel::class.java).findAll()

        //delete from 0 index

        if (dataModels.size == MAX_DOCUMENTS_IN_DB) {
            Log.d(TAG, "objects greater than $MAX_DOCUMENTS_IN_DB")

//            for (i in (0..dataModels.size-2) ){
//                dataModels[i].
//            }
            realm!!.executeTransaction {

                dataModels[0].deleteFromRealm()
            }

//            var val3 = dataModels[3].apiUrl
//            Log.d(TAG,"value at 3 $val3")
            var val0 = dataModels[0].apiUrl
            Log.d(TAG, "value at 0 $val0")
        }

    }

    fun addToDB(apiUrl: String, req: String, res: String) {
        try {
            manageDB()
            dataModelGlobal.apiUrl = apiUrl
            dataModelGlobal.apiRequest = req
            dataModelGlobal.apiResponse = res

            realm!!.executeTransaction { realm -> realm.copyToRealm(dataModelGlobal) }

            //Log.d(TAG,"dataModelGlobal in submit "+dataModelGlobal.toString())

            Log.d(TAG, "Api Data Inserted in DB!!! $apiUrl")


        } catch (e: Exception) {
            Log.d(TAG, "Something went Wrong !!!")
        }
    }

    fun fetchHistoryOfApis() {
        try {
            val apiHistoryListView = getApiHistoryListView()
            apiHistoryListView.let {
                val dataModels: List<ApiDataModel> =
                    realm!!.where(ApiDataModel::class.java).findAll()
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

    abstract fun getApiHistoryListView() : ListView?
}