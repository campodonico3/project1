package dev.campodonico3.project1.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dev.campodonico3.project1.R
import dev.campodonico3.project1.ViewModel.MainViewModel
import dev.campodonico3.project1.adapter.ItemsAdapter
import dev.campodonico3.project1.databinding.ActivityItemListBinding

class ItemListActivity : AppCompatActivity() {
    lateinit var binding: ActivityItemListBinding
    private val viewModel = MainViewModel()
    private var id: String = ""
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundles()
        initList()
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            viewModel.loadItemsCategory(id).observe(this@ItemListActivity, Observer {
                listView.layoutManager = GridLayoutManager(this@ItemListActivity, 2)
                listView.adapter = ItemsAdapter(it)
                progressBar.visibility = View.GONE
            })
            backBtn.setOnClickListener { finish() }
        }
    }

    private fun getBundles() {
        id = intent.getStringExtra("id")!!
        title = intent.getStringExtra("title")!!

        binding.categoryText.text = title
    }
}