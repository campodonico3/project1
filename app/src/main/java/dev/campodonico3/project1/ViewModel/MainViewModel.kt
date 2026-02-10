package dev.campodonico3.project1.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.campodonico3.project1.domain.CategoryDomain

class MainViewModel : ViewModel() {
    private val repository = MainViewModel()

    fun loadCategory(): LiveData<MutableList<CategoryDomain>> {
        return repository.loadCategory()
    }
}