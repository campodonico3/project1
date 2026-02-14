package dev.campodonico3.project1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.campodonico3.project1.databinding.ViewholderItemBinding
import dev.campodonico3.project1.domain.ItemsModel

class ItemsAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<ItemsAdapter.Viewholder>() {

    lateinit var context: Context

    class Viewholder(val binding: ViewholderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemsAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(
        holder: Viewholder,
        position: Int
    ) {
        holder.binding.titleTxt.text = items[position].title
        holder.binding.priceTxt.text = "$" + items[position].price.toString()
        holder.binding.subtitleTxt.text = items[position].extra

        Glide.with(context).load(items[position].picUrl[0]).into(holder.binding.pic)
    }

    override fun getItemCount(): Int = items.size
}