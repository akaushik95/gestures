package com.example.gestures.models

import java.io.File

data class ApiFormData(
    val country: String,
    val summary: String,
    val description: String,
    val file: File

)