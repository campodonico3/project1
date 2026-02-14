package dev.campodonico3.project1.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.campodonico3.project1.R
import dev.campodonico3.project1.databinding.ViewholderCategoryBinding
import dev.campodonico3.project1.domain.CategoryModal

// Clase que adapta los datos para mostrarlos en un RecyclerView
class CategoryAdapter(val items: MutableList<CategoryModal>) :
    RecyclerView.Adapter<CategoryAdapter.Viewholder>() {

    private lateinit var context: Context // Contexto de la aplicación
    private var selectedPosition = -1 // Posición del elemento actualmente seleccionado (-1 = ninguno)
    private var lastSelectedPosition = -1 // Posición del elemento previamente seleccionado (para deseleccionarlo)

    class Viewholder(val binding: ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    // Crear vistas
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return Viewholder(binding)
    }

    // Se ejecuta cada vez que una vista necesita motrar datos
    override fun onBindViewHolder(holder: CategoryAdapter.Viewholder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position] // Obtiene la categoría en la posición actual
        holder.binding.titleCat.text = item.title // Muestra el nombre de la categoría en el TextView

        // Menejo del clic del elemento
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition // Guarda la posición anterior
            selectedPosition = position             // Actualizar a la nueva posición
            notifyItemChanged(lastSelectedPosition) // Redibujar el elemento anterior
            notifyItemChanged(selectedPosition)     // Redibujar el elemento actual

            Handler(Looper.getMainLooper()).postDelayed({

            }, 500)
        }

        // Cambiar apariencia según la selección
        if (selectedPosition == position) {
            // Elemento SELECCIONADO
            holder.binding.titleCat.setBackgroundResource(R.drawable.brown_full_corner)
            holder.binding.titleCat.setTextColor(context.resources.getColor(R.color.white))
        } else {
            // Elemento NO SELECCIONADO
            holder.binding.titleCat.setBackgroundResource(R.drawable.cream_full_corner)
            holder.binding.titleCat.setTextColor(context.resources.getColor(R.color.darkBrown))
        }
    }

    override fun getItemCount(): Int = items.size
}