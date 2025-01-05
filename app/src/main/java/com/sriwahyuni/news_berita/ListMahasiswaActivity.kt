package com.sriwahyuni.news_berita

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sriwahyuni.news_berita.adapter.MahasiswaAdapter
import com.sriwahyuni.news_berita.model.MahasiswaResponse
import com.sriwahyuni.news_berita.model.ModelMahasiswa
import com.sriwahyuni.news_berita.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMahasiswaActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var recycleview  : RecyclerView
    private lateinit var call : Call<MahasiswaResponse>
    private lateinit var mahasiswaAdapter : MahasiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_mahasiswa)

        swipeRefreshLayout = findViewById(R.id.refresh)
        recycleview = findViewById(R.id.rv_mahasiswa)

        mahasiswaAdapter = MahasiswaAdapter{modelMahasiswa : ModelMahasiswa ->(modelMahasiswa) }
        recycleview.adapter = mahasiswaAdapter

        recycleview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,
            false)

        swipeRefreshLayout.setOnRefreshListener {
            getData() //metho untuk proses ambil data
        }

        getData()



    }

    private fun getData() {
        swipeRefreshLayout.isRefreshing = true
        call = ApiClient.retrofit.getAllMahasiswa()
        call.enqueue(object : Callback<MahasiswaResponse> {
            override fun onResponse(
                call: Call<MahasiswaResponse>,
                response: Response<MahasiswaResponse>
            ) {
                swipeRefreshLayout.isRefreshing = false
                if(response.isSuccessful){
                    mahasiswaAdapter.submitList(response.body()?.data)
                    mahasiswaAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MahasiswaResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(applicationContext, t.localizedMessage,
                    Toast.LENGTH_SHORT).show()
            }

        })
    }
}