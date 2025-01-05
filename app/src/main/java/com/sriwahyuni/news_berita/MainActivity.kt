package com.sriwahyuni.news_berita

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sriwahyuni.news_berita.adapter.BeritaAdapter
import com.sriwahyuni.news_berita.model.ModelBerita
import com.sriwahyuni.news_berita.model.ResponseBerita
import com.sriwahyuni.news_berita.screen.TambahDataUserActivity
import com.sriwahyuni.news_berita.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var call: Call<ResponseBerita>
    private lateinit var beritaAdapter: BeritaAdapter
    private lateinit var btntambahdata: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inisialisasi View
        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        recyclerView = findViewById(R.id.rv_berita)
        btntambahdata = findViewById(R.id.btntambahdata)

        // Setup Adapter
        beritaAdapter = BeritaAdapter { modelBerita: ModelBerita -> beritaOnClick(modelBerita) }
        recyclerView.adapter = beritaAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            applicationContext, LinearLayoutManager.VERTICAL,
            false
        )

        // Set OnClickListener untuk Button Tambah Data
        btntambahdata.setOnClickListener {
            val intent = Intent(this, TambahDataUserActivity::class.java)
            startActivity(intent)
        }

        // Swipe Refresh untuk mengambil data
        swipeRefreshLayout.setOnRefreshListener {
            getData()
        }

        // Ambil data awal
        getData()
    }

    // Fungsi untuk menangani klik item berita
    private fun beritaOnClick(modelBerita: ModelBerita) {
        val intent = Intent(this, DetailBerita::class.java).apply {
            putExtra("gambar", modelBerita.gambar_berita)
            putExtra("judul", modelBerita.judul)
            putExtra("isi", modelBerita.isi_berita)
            putExtra("tanggal", modelBerita.tgl_berita)
        }
        startActivity(intent)
    }

    // Fungsi untuk mengambil data berita dari API
    private fun getData() {
        swipeRefreshLayout.isRefreshing = true
        call = ApiClient.retrofit.getAllBerita()
        call.enqueue(object : Callback<ResponseBerita> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseBerita>,
                response: Response<ResponseBerita>
            ) {
                swipeRefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
                    beritaAdapter.submitList(response.body()?.data)
                    beritaAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Gagal mendapatkan data!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBerita>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(
                    applicationContext,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}