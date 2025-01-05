package com.sriwahyuni.news_berita.model

import java.io.Serializable

data class ModelBerita(
    val id : Int,
    val judul : String,
    val isi_berita : String,
    val tgl_berita : String,
    val gambar_berita : String,
)

