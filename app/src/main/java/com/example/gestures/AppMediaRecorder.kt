package com.example.gestures

import android.media.MediaRecorder

object AppMediaRecorder {

    private var mediaRecorder: MediaRecorder? = null

    fun fetchMediaRecorder() : MediaRecorder {
        if(mediaRecorder == null) {
            mediaRecorder = MediaRecorder()
        }
        return mediaRecorder!!
    }
}