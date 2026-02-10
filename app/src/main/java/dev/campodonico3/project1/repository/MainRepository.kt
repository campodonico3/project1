package dev.campodonico3.project1.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.campodonico3.project1.domain.CategoryDomain

// Clase responsable de obtener los datos de Firebase
class MainRepository {
    // Instancia única de Firebase Realtime Database para acceder a los datos.
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadCategory(): LiveData<MutableList<CategoryDomain>> {
        // MutableLiveData -> Versión editable de LiveData que permite cambiar su valor
        val listData = MutableLiveData<MutableList<CategoryDomain>>()

        // Obtenemos la referencia del nodo "Category" en Firebase
        val ref = firebaseDatabase.getReference("Category")

        // Se suscribe a cambios en tienpo real del nodo "Category"
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Creamos la lista vacía
                val list = mutableListOf<CategoryDomain>()

                // Iteramos sobre los hijos
                for (childSnapshot in snapshot.children) {
                    // Convertimos automaticamente los datos de Firebase a un objeto "CategoryDomain"
                    val item = childSnapshot.getValue(CategoryDomain::class.java)

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
                // ✅ IMPLEMENTACIÓN CORRECTA
                Log.e("MainRepository", "Error Firebase: ${error.message}")
                Log.e("MainRepository", "Código de error: ${error.code}")
                Log.e("MainRepository", "Detalles: ${error.details}")

                // Enviar lista vacía para no crashear la app
                listData.value = mutableListOf()
            }

        })
        return listData
    }
}