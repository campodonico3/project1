package dev.campodonico3.project1.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.campodonico3.project1.domain.CategoryDomain
import dev.campodonico3.project1.repository.MainRepository

class MainViewModel : ViewModel() {
    /*
    * private: Solo accesible dentro de MainViewModel.
    * val: Inmutable, se crea una sola vez.
    * MainRepository(): Crea una instancia del repositorio que se conecta a Firebase.
    * Propósito: El ViewModel delega la obtención de datos al Repository (patrón MVVM).
    * */
    private val repository = MainRepository()

    // Actúa como intermediario entre la UI y el Repository
    fun loadCategory(): LiveData<MutableList<CategoryDomain>> {
        return repository.loadCategory()
    }
}

/*
## **Arquitectura MVVM - Flujo completo:**
```
┌─────────────┐
│  Activity   │  (UI - Vista)
│ (MainActivity)
└──────┬──────┘
       │ observa LiveData
       │
┌──────▼──────┐
│  ViewModel  │  (Lógica de presentación)
│(MainViewModel)
└──────┬──────┘
       │ llama a loadCategory()
       │
┌──────▼──────┐
│ Repository  │  (Acceso a datos)
│(MainRepository)
└──────┬──────┘
       │ consulta datos
       │
┌──────▼──────┐
│  Firebase   │  (Fuente de datos)
│(Realtime DB)│
└─────────────┘

Ventajas:
✅ Los datos sobreviven a rotaciones de pantalla
✅ No se duplican las llamadas a Firebase
✅ Mejor rendimiento y experiencia de usuario
*/
