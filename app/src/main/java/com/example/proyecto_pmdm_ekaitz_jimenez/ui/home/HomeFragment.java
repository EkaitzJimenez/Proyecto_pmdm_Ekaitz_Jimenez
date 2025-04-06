package com.example.proyecto_pmdm_ekaitz_jimenez.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_pmdm_ekaitz_jimenez.CrearLibroActivity;
import com.example.proyecto_pmdm_ekaitz_jimenez.R;
import com.example.proyecto_pmdm_ekaitz_jimenez.database.AppDatabase;
import com.example.proyecto_pmdm_ekaitz_jimenez.dao.LibroDao;
import com.example.proyecto_pmdm_ekaitz_jimenez.models.Libro;
import com.example.proyecto_pmdm_ekaitz_jimenez.LibroAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Fragmento principal: muestra la lista de libros
 *
 */
public class HomeFragment extends Fragment {

    // ReciclerView que muestra la lista de libros
    private RecyclerView recyclerView;
    // adaptador para el RecyclerView
    private LibroAdapter adapter;
    // Lista de libros
    private List<Libro> listaLibros;
    // código de solicitud para la actividad CrearLibroActivity
    private static final int REQUEST_CODE_NUEVO_LIBRO = 1;

    // ExecutorService que ejecuta tareas en un hilo secundario
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Método para crear la vista del fragmento
     * @param inflater objeto LayoutInflater para inflar cualquier vista en el fragmento.
     * @param container si no es nulo, el fragmento se inflará en este ViewGroup
     * @param savedInstanceState Si no es nulo, el fragmento se reconstruye
     * a partir de un estado guardado previamente.
     *
     * @return La vista inflada que se muestra en el fragmento
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflar el layout del fragmento, enlazar la variable root
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializar el RecyclerView y asignar un LayoutManager
        recyclerView = root.findViewById(R.id.recyclerViewLibros);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configurar el FloatingActionButton para agregar un nuevo libro
        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            //iniciar la actividad CrearLibroActivity para añadir un libro
            Intent intent = new Intent(getActivity(), CrearLibroActivity.class);
            startActivityForResult(intent, REQUEST_CODE_NUEVO_LIBRO);
        });

        // Cargar los libros desde la bd
        cargarLibros();

        return root; //devolver root
    }

    /**
     * Método llamado cuando el fragmento vuelve al primer plano.
     * Vuelve a cargar la lista de libros, la actualioza
     */
    @Override
    public void onResume() {
        super.onResume();
        // Cargar los libros de nuevo -> Actualizar la lista
        cargarLibros();
    }

    /**
     * Método que carga los libros desde la bd
     * carga de datos en un hilo secundario para evitar
     * bloquear el hilo principal, y actualiza la UI con los datos obtenidos
     */
    private void cargarLibros() {
        try {
            // Acceder a la bd usando AppDatabase
            AppDatabase db = AppDatabase.getInstance(getContext());
            LibroDao libroDao = db.libroDao();

            // Ejecutar la carga de libros en un hilo secundario para no bloquear el hilo principal
            executorService.execute(() -> {
                // Obtener todos los libros
                listaLibros = libroDao.obtenerTodos();

                // Actualizar el RecyclerView en el hilo principal
                getActivity().runOnUiThread(() -> {
                    // Asignar el adaptador con la lista de libros y asociar al RecyclerView
                    adapter = new LibroAdapter(listaLibros);
                    recyclerView.setAdapter(adapter);
                });
            });
        } catch (Exception e) {
            // En caso de error -> mostrar log
            Log.e("HomeFragment", "Error al cargar libros", e);
        }
    }

}
