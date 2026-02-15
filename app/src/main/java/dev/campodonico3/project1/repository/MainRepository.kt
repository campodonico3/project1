package dev.campodonico3.project1.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import dev.campodonico3.project1.domain.BannerModel
import dev.campodonico3.project1.domain.CategoryModel
import dev.campodonico3.project1.domain.ItemsModel

// Clase responsable de obtener los datos de Firebase
class MainRepository {
    // Instancia única de Firebase Realtime Database para acceder a los datos.
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        // MutableLiveData -> Versión editable de LiveData que permite cambiar su valor
        val listData = MutableLiveData<MutableList<CategoryModel>>()

        // Obtenemos la referencia del nodo "Category" en Firebase
        val ref = firebaseDatabase.getReference("Category")

        // Se suscribe a cambios en tienpo real del nodo "Category"
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Creamos la lista vacía
                val list = mutableListOf<CategoryModel>()

                // Iteramos sobre los hijos
                for (childSnapshot in snapshot.children) {
                    // Convertimos automaticamente los datos de Firebase a un objeto "CategoryDomain"
                    val item = childSnapshot.getValue(CategoryModel::class.java)

                    // Añadimos a la lista, si no es null
                    item?.let {
                        list.add(it)
                        Log.d("MainRepository", "Categoría cargada: ${it.title}")
                    }
                }
                Log.d("MainRepository", "Total categorías: ${list.size}")
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainRepository", "Error Firebase: ${error.message}")
                Log.e("MainRepository", "Código de error: ${error.code}")
                Log.e("MainRepository", "Detalles: ${error.details}")

                // Enviar lista vacía para no crashear la app
                listData.value = mutableListOf()
            }

        })
        return listData
    }

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        // MutableLiveData -> Versión editable de LiveData que permite cambiar su valor
        val listData = MutableLiveData<MutableList<BannerModel>>()

        // Obtenemos la referencia del nodo "Banner" en Firebase
        val ref = firebaseDatabase.getReference("Banner")

        // Se suscribe a cambios en tienpo real del nodo "Banner"
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Creamos la lista vacía
                val list = mutableListOf<BannerModel>()

                // Iteramos sobre los hijos
                for (childSnapshot in snapshot.children) {
                    // Convertimos automaticamente los datos de Firebase a un objeto "BannerModel"
                    val item = childSnapshot.getValue(BannerModel::class.java)

                    // Añadimos a la lista, si no es null
                    item?.let {
                        list.add(it)
                    }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                listData.value = mutableListOf()
            }

        })
        return listData
    }

    fun loadPopular(): LiveData<MutableList<ItemsModel>> {
        // MutableLiveData -> Versión editable de LiveData que permite cambiar su valor
        val listData = MutableLiveData<MutableList<ItemsModel>>()

        // Obtenemos la referencia del nodo "Category" en Firebase
        val ref = firebaseDatabase.getReference("Popular")

        // Se suscribe a cambios en tienpo real del nodo "Category"
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Creamos la lista vacía
                val list = mutableListOf<ItemsModel>()

                // Iteramos sobre los hijos
                for (childSnapshot in snapshot.children) {
                    // Convertimos automaticamente los datos de Firebase a un objeto "CategoryDomain"
                    val item = childSnapshot.getValue(ItemsModel::class.java)

                    // Añadimos a la lista, si no es null
                    item?.let {
                        list.add(it)
                    }
                }
                Log.d("MainRepository", "Total categorías: ${list.size}")
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                // Enviar lista vacía para no crashear la app
                listData.value = mutableListOf()
            }

        })
        return listData
    }

    fun loadItemsCategory(categoryId: String): LiveData<MutableList<ItemsModel>> {
        // MutableLiveData -> Versión editable de LiveData que permite cambiar su valor
        val listData = MutableLiveData<MutableList<ItemsModel>>()

        // Obtenemos la referencia del nodo "Category" en Firebase
        val ref = firebaseDatabase.getReference("Items")
        val query: Query = ref.orderByChild("categoryId").equalTo(categoryId)

        // Se suscribe a cambios en tienpo real del nodo "Category"
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ItemsModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                listData.value = mutableListOf()
            }

        })
        return listData
    }
}