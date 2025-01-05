package com.sriwahyuni.news_berita.service

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Arrays

object ApiClient {

    ////https://dummyjson.com/products
    private const val BASE_URL = "http://192.168.243.106/beritaDb/"


     val retrofit: BeritaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(interceptor())  // fungsi di bawah
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeritaService::class.java)
    }

    private val client = OkHttpClient.Builder()
        .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
        .addInterceptor{ chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-type", "application/jason")
                .build()
            chain.proceed(request)
        }
        .build()

    // Fungsi interceptor

    fun interceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().
        addInterceptor(interceptor).build()
    }

    // Membuat dan menyediakan instance ProdukService
//    val beritaService: BeritaService by lazy {
//        retrofit.create(BeritaService::class.java)
//    }
}
