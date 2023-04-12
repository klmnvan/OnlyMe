package com.example.restartonlyme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restartonlyme.databinding.ItemNewsBinding

class AdapterNews: RecyclerView.Adapter<AdapterNews.Holder>() {
    var listNews = ArrayList<NewsModel>()

    class Holder (item: View): RecyclerView.ViewHolder(item){
        var binding = ItemNewsBinding.bind(item)

        fun bind(news: NewsModel){
            with(binding){
                name.setText(news.name)
                des.setText(news.description)
                price.setText(news.price + " P")
                Glide.with(binding.root).load(news.image).into(binding.image)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNews.Holder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: AdapterNews.Holder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    fun addNews(news: NewsModel){
        listNews.add(news)
    }
}