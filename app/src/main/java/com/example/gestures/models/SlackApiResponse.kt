package com.example.gestures.models

import com.google.gson.annotations.SerializedName

data class SlackApiResponse (
    @SerializedName("ts")
    val threadTimestamp: String
    )