package dev.campodonico3.project1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import dev.campodonico3.project1.Helper.ChangeNumberItemsListener
import dev.campodonico3.project1.Helper.ManagmentCart
import dev.campodonico3.project1.databinding.ViewholderCartBinding
import dev.campodonico3.project1.domain.ItemsModel

class CartAdapter(
    private val listItemSelected: ArrayList<ItemsModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener? = null
) : RecyclerView.Adapter<CartAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val managmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.Viewholder {
        val binding =
            ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.Viewholder, position: Int) {
        val item = listItemSelected[position]
        holder.binding.apply {
            titleText.text = item.title
            feeEachItem.text = "$${item.numberInCart * item.price}"
            totalEachItem.text = item.numberInCart.toString()

            Glide.with(holder.itemView.context).load(item.picUrl[0]).apply(
                RequestOptions().transform(
                    CenterCrop()
                )
            ).into(picCart)

            plusBtn.setOnClickListener {
                managmentCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener?.onChanged()
                    }
                })
            }

            minusBtn.setOnClickListener {
                managmentCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener?.onChanged()
                    }
                })
            }

            removeItemBtn.setOnClickListener {
                managmentCart.removeItem(listItemSelected, position, object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener?.onChanged()
                    }
                })
            }
        }


    }

    override fun getItemCount(): Int = listItemSelected.size

}