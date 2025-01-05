package com.sriwahyuni.news_berita.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sriwahyuni.news_berita.R
import com.sriwahyuni.news_berita.model.ModelBerita



class BeritaAdapter(
    private val onClick : (ModelBerita) -> Unit
) : ListAdapter<ModelBerita, BeritaAdapter.BeritaViewHolder>(ProdukCallback) {
    class BeritaViewHolder (itemView: View, val onClick: (ModelBerita) -> Unit): RecyclerView.ViewHolder(itemView){

        private val imgBerita : ImageView = itemView.findViewById(R.id.imgBerita)
        private val txtJudul : TextView = itemView.findViewById(R.id.judul)
        private val txtIsiBerita : TextView = itemView.findViewById(R.id.isi_berita)
        private val txtTglBerita : TextView = itemView.findViewById(R.id.tgl_berita)

        //cek produk saat ini
        private var currentBerita : ModelBerita? = null

        init {
            itemView.setOnClickListener(){
                currentBerita?.let {
                    onClick(it)
                }
            }
        }
        fun bind(berita: ModelBerita){
            currentBerita = berita
            //set data ke widget
            txtJudul.text = berita.judul
            txtIsiBerita.text = berita.isi_berita
            txtTglBerita.text = berita.tgl_berita
            //untuk menampilkan gambar pad widget (fetching gambar dengan glide)
            Glide.with(itemView).load("http://192.168.243.106/beritaDb/gambar_berita/"
                    + berita.gambar_berita).centerCrop().into(imgBerita)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_berita,parent,false)
        return BeritaViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        val produk = getItem(position)
        holder.bind(produk)
    }
}

object ProdukCallback : DiffUtil.ItemCallback<ModelBerita>(){
    override fun areItemsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem == newItem
    }
}