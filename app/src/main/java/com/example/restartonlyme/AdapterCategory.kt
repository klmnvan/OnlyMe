package com.example.restartonlyme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restartonlyme.databinding.ItemCategoryBinding

class AdapterCategory: RecyclerView.Adapter<AdapterCategory.Holder>() {
    var listCategory = ArrayList<String>()

    class Holder(item: View): RecyclerView.ViewHolder(item){
        var binding =ItemCategoryBinding.bind(item)

        fun bind(category: String){
            with(binding){
                textView2.setText(category)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategory.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: AdapterCategory.Holder, position: Int) {
        holder.bind(listCategory[position])
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    fun addCategory(category: String){
        listCategory.add(category)
    }

}