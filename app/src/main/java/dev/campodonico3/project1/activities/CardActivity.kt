package dev.campodonico3.project1.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.campodonico3.project1.Helper.ChangeNumberItemsListener
import dev.campodonico3.project1.Helper.ManagmentCart
import dev.campodonico3.project1.R
import dev.campodonico3.project1.adapter.CartAdapter
import dev.campodonico3.project1.databinding.ActivityCardBinding

class CardActivity : AppCompatActivity() {
    lateinit var binding: ActivityCardBinding
    lateinit var managmentCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        calculatorCart()
        setVariable()
        initCartList()
    }

    private fun initCartList() {
        binding.apply {
            listView.layoutManager =
                LinearLayoutManager(this@CardActivity, LinearLayoutManager.VERTICAL, false)
            listView.adapter = CartAdapter(
                managmentCart.getListCart(),
                this@CardActivity,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculatorCart()
                    }

                }
            )
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun calculatorCart() {
        val percentTax = 0.02
        val delivery = 15
        tax = ((managmentCart.getTotalFee() * percentTax) * 100) / 100.0
        val total = ((managmentCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = (managmentCart.getTotalFee() * 100) / 100

        binding.apply {
            totalFreeTxt.text = "$$itemTotal"
            totalTaxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }
}