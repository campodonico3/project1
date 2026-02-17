package dev.campodonico3.project1.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import dev.campodonico3.project1.Helper.ManagmentCart
import dev.campodonico3.project1.R
import dev.campodonico3.project1.R.*
import dev.campodonico3.project1.databinding.ActivityDetailBinding
import dev.campodonico3.project1.domain.ItemsModel

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        bundle()
        initSizeList()
    }

    private fun initSizeList() {
        binding.apply {
            smallBtn.setOnClickListener {
                smallBtn.setBackgroundResource(R.drawable.brown_full_corner)
                smallBtn.setTextColor(getResources().getColor(R.color.white))
                mediumBtn.setBackgroundResource(0)
                mediumBtn.setTextColor(getResources().getColor(R.color.black))
                largeBtn.setBackgroundResource(0)
                largeBtn.setTextColor(getResources().getColor(R.color.black))
            }

            mediumBtn.setOnClickListener {
                smallBtn.setBackgroundResource(0)
                smallBtn.setTextColor(getResources().getColor(R.color.black))
                mediumBtn.setBackgroundResource(R.drawable.brown_full_corner)
                mediumBtn.setTextColor(getResources().getColor(R.color.white))
                largeBtn.setBackgroundResource(0)
                largeBtn.setTextColor(getResources().getColor(R.color.black))
            }

            largeBtn.setOnClickListener {
                smallBtn.setBackgroundResource(0)
                smallBtn.setTextColor(getResources().getColor(R.color.black))
                mediumBtn.setBackgroundResource(0)
                mediumBtn.setTextColor(getResources().getColor(R.color.black))
                largeBtn.setBackgroundResource(R.drawable.brown_full_corner)
                largeBtn.setTextColor(getResources().getColor(R.color.white))
            }
        }
    }

    private fun bundle() {
        binding.apply {
            item = intent.getSerializableExtra("object") as ItemsModel

            Glide.with(this@DetailActivity).load(item.picUrl[0]).into(binding.pickMain)

            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = "$" + item.price
            ratingTxt.text = item.rating.toString()

            item.numberInCart = 1

            addToCartBtn.setOnClickListener {
                item.numberInCart = Integer.valueOf(numberInCart.text.toString())
                managmentCart.insertItems(item)
            }

            backBtn.setOnClickListener { finish() }

            plusBtn.setOnClickListener {
                numberInCart.text = (item.numberInCart + 1).toString()
                item.numberInCart++
            }

            minusBtn.setOnClickListener {
                if (item.numberInCart > 0) {
                    numberInCart.text = (item.numberInCart - 1).toString()
                    item.numberInCart--
                }
            }
        }

    }
}