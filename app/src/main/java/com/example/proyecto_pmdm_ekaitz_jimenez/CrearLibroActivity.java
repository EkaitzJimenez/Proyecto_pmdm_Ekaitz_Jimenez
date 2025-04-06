package com.example.proyecto_pmdm_ekaitz_jimenez;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_pmdm_ekaitz_jimenez.database.AppDatabase;
import com.example.proyecto_pmdm_ekaitz_jimenez.dao.LibroDao;
import com.example.proyecto_pmdm_ekaitz_jimenez.models.Libro;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CrearLibroActivity: permite al usuario crear un nuevo libro y guardarlo en la bd
 * tiene campos para titulo, autor, pagnas, género y estado de lectura.
 */
public class CrearLibroActivity extends AppCompatActivity {

    //Estado de lectura seleccionado en el spinner
    private String estadoLecturaSeleccionado;

    //Executor para operaciones de bd en segundo plano
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Método para crear la actividad. Inicializa la vista, el toolbar y los listeners.
     *
     * @param savedInstanceState Bundle con el estado previamente guardado
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //llama al método onCreate del padre para inicializar la actividad
        super.onCreate(savedInstanceState);

        //Enlazar la actividad con el layout
        setContentView(R.layout.activity_crear_libro);

        //mantener los bordes de la vista por encima del resto
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Configuración del toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configuración del botón para guardar el libro
        Button clickButton = findViewById(R.id.btnGuardar);
        clickButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Listener del botón para guardar un nuevo libro
             *
             * @param v Vista que se clicó
             */
            @Override
            public void onClick(View v) {
                //llamar al metodo para insertar y mostrar libros
                insertarYMostrarLibros();
            }
        });

        // Configurar el Spinner para el estado de lectura
        configurarSpinner();
    }

    /**
     * Configura el Spinner(dropdown) para seleccionar el estado de lectura del libro.
     *Cargar el contenido desde los recursos y establecer el comportamiento
     */
    private void configurarSpinner() {
        // Obtener el Spinner desde el layout
        Spinner spinnerEstadoLectura = findViewById(R.id.spinner_estado_lectura);

        //crear un adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.estados_lectura,
                R.layout.spinner_item
        );

        //establecer el diseño del elemento seleccionado
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el adaptador en el Spinner
        spinnerEstadoLectura.setAdapter(adapter);

        // Establecer el estado de lectura seleccionado
        spinnerEstadoLectura.setSelection(adapter.getPosition(getIntent().getStringExtra("estadoLectura")), false);
        estadoLecturaSeleccionado = getIntent().getStringExtra("estadoLectura");

        // Establecer el comportsmiento del spiner
        spinnerEstadoLectura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Actualizar la variable con el estado de lectura seleccionado.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el estado de lectura seleccionado
                estadoLecturaSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona nada
            }
        });
    }

    /**
     * Insertar un nuevo libro en la base de datos y muestra un Toast
     * Si hay campos vacios o errores de formato, notificarlos
     */
    private void insertarYMostrarLibros() {
        // Obtener los datos del formulrio
        EditText etTitulo = findViewById(R.id.etTitulo);
        EditText etAutor = findViewById(R.id.etAutor);
        EditText etPaginas = findViewById(R.id.etPaginas);
        EditText etGenero = findViewById(R.id.etGenero);

        // Obtener los datos del formulario
        String titulo = etTitulo.getText().toString().trim();
        String autor = etAutor.getText().toString().trim();
        String paginasStr = etPaginas.getText().toString().trim();
        String genero = etGenero.getText().toString().trim();

        // validar que los campos no esten vacios
        if (titulo.isEmpty() || autor.isEmpty() || paginasStr.isEmpty() || genero.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que el numro de páginas sea un número valido
        int paginas;
        try {
            paginas = Integer.parseInt(paginasStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Número de páginas inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que se haya seleccionado un estado de lectura
        if (estadoLecturaSeleccionado == null || estadoLecturaSeleccionado.isEmpty()) {
            Toast.makeText(this, "Selecciona un estado de lectura", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecutar la operación de base de datos en segundo plano
        executorService.execute(() -> {
            // Guardar en la base de datos
            AppDatabase db = AppDatabase.getInstance(this);
            LibroDao libroDao = db.libroDao();

            // Crear un nuevo libro con todos los datos incluyendo el estado de lectura
            Libro nuevoLibro = new Libro(titulo, autor, paginas, genero, estadoLecturaSeleccionado);

            // Insertar el libro en la base de datos
            libroDao.insertar(nuevoLibro);

            // Actualizar la UI después de realizar la operación
            runOnUiThread(() -> {
                Toast.makeText(this, "Libro guardado correctamente!", Toast.LENGTH_SHORT).show();

                // Enviar resultado de éxito a MainActivity
                setResult(RESULT_OK);

                finish(); // Cerrar la actividad -> volver al activity main
            });
        });
    }

}
