package com.example.proyecto_pmdm_ekaitz_jimenez;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_pmdm_ekaitz_jimenez.models.Libro;

import java.util.List;

/**
 * Adaptador que muestra una lista de libros en un RecyclerView
 * Cada elemento tiene: título, autor y estado de lectura del libro.
 */
public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {
    // Lista de libros a mostrar
    private List<Libro> listaLibros;

    /**
     * Constructor que recibe una lista de libros
     * @param listaLibros Lista de libros que se mostraran en el RecyclerView
     */
    public LibroAdapter(List<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }

    /**
     * Crear un nuevo ViewHolder para cada item del RecyclerView.
     * @param parent El contenedor donde se insertará la vista del item.
     * @param viewType El tipo de vista(no se usa)
     * @return Un nuevo ViewHolder con la vista inflada.
     */
    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout para cada tarjeta de libro
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        // Crear y devolver un nuevo ViewHolder
        return new LibroViewHolder(view);
    }

    /**
     * Asociar los datos de un libro con su vista
     * @param holder ViewHolder contiene las vistas del item
     * @param position posición de libro en la lista
     */
    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        // Obtener un libro de la lista
        Libro libro = listaLibros.get(position);

        // Asignar los datos del libro a las vistas
        holder.titulo.setText(libro.getTitulo());
        holder.autor.setText(libro.getAutor());
        holder.estadoLectura.setText(libro.getEstadoLectura());

        // acción al hacer clic en un item: abrir actividad para editar el libro
        holder.itemView.setOnClickListener(v -> {
            // Crear un intent para abrir la actividad de edición

            Intent intent = new Intent(v.getContext(), EditLibro.class);

            // Pasar los datos del libro a la actividad de edición
            intent.putExtra("id", libro.getId());
            intent.putExtra("titulo", libro.getTitulo());
            intent.putExtra("autor", libro.getAutor());
            intent.putExtra("paginas", libro.getPaginas());
            intent.putExtra("genero", libro.getGenero());
            intent.putExtra("estadoLectura", libro.getEstadoLectura());

            // Iniciar la actividad de edición
            v.getContext().startActivity(intent);
        });
    }

    /**
     * Devoler el número total de libros en el RecyclerView.
     * @return número de libros en la lista.
     */
    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    /**
     * ViewHolder mantiene las vistas de cada item en el RecyclerView
     * Contiene las vistas de título, autor y estado de lectura
     */
    public static class LibroViewHolder extends RecyclerView.ViewHolder {
        // Vistas de la tarjeta de libro
        TextView titulo, autor, estadoLectura;

        /**
         * Constructor: inicializa las vistas del item.
         * @param itemView La vista del item inflada
         */
        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar las vistas de la tarjeta de libro
            titulo = itemView.findViewById(R.id.title);
            autor = itemView.findViewById(R.id.subhead);
            estadoLectura = itemView.findViewById(R.id.estadoLectura);
        }
    }
}
