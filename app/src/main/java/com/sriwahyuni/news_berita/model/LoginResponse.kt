package com.sriwahyuni.news_berita.model

import androidx.media3.common.util.HandlerWrapper.Message

class LoginResponse (
    val success : Boolean,
    val message: String,
    val value: Int
)