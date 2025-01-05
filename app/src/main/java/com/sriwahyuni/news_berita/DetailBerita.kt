package com.sriwahyuni.news_berita

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class DetailBerita : AppCompatActivity() {

    private lateinit var imgThumbnail: ImageView
    private lateinit var txtJudul: TextView
    private lateinit var txtTanggal: TextView
    private lateinit var txtIsi: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)

        // Hubungkan dengan View
        imgThumbnail = findViewById(R.id.imgProduk)
        txtJudul = findViewById(R.id.txtJudul)
        txtTanggal = findViewById(R.id.txtTanggal)
        txtIsi = findViewById(R.id.txtIsi)


        //getData
        val gambar = intent.getStringExtra("gambar_berita")
        val judul = intent.getStringExtra("judul")
        val isi = intent.getStringExtra("isi_berita")
        val tanggal = intent.getStringExtra("tgl_berita")


        Glide.with(this).load("http://192.168.33.252/beritaDb/gambar_berita/"
                + gambar).centerCrop().into(imgThumbnail)
        txtJudul.text = judul
        txtTanggal.text = tanggal
        txtIsi.text = isi
    }
}

