package com.example.proyecto_pmdm_ekaitz_jimenez;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_pmdm_ekaitz_jimenez.database.AppDatabase;
import com.example.proyecto_pmdm_ekaitz_jimenez.models.Libro;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Actividad para editar o eliminar un libro en la bd
 * Muestra los datos actuales del libro en un formulario para editarlo
 */
public class EditLibro extends AppCompatActivity {
    // Estado de lectura sleccionado en el spinner
    private String estadoLecturaSeleccionado;
    // Referencia al spinner para el estado de lectura
    private Spinner spinnerEstadoLectura;

    // Ejecuta operaciones de base de datos en segundo plano
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Método llamado al crear la actividad.
     * Inicializar el formularrio con los datos actuales del libro.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamar al método onCreate del padre para inicializar la actividad
        super.onCreate(savedInstanceState);
        // Asociar de la actividad con el layout
        setContentView(R.layout.activity_edit_libro);

        // Aplicar padding para respetar las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //configurar el toolbar con botón de retroceso
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Referencias a los campos del formulario
        EditText etTitulo = findViewById(R.id.etTitulo), etAutor = findViewById(R.id.etAutor),
                etPaginas = findViewById(R.id.etPaginas), etGenero = findViewById(R.id.etGenero);
        spinnerEstadoLectura = findViewById(R.id.spinner_estado_lectura);

        // Cargar los datos actuales del libro desde el intent
        int idLibro = getIntent().getIntExtra("id", -1);
        etTitulo.setText(getIntent().getStringExtra("titulo"));
        etAutor.setText(getIntent().getStringExtra("autor"));
        etPaginas.setText(String.valueOf(getIntent().getIntExtra("paginas", 0)));
        etGenero.setText(getIntent().getStringExtra("genero"));

        // Configurar el spinner
        configurarSpinner();

        // Accion de los botones
        findViewById(R.id.btnEditar).setOnClickListener(v -> editarLibro(etTitulo, etAutor, etPaginas, etGenero, idLibro));
        findViewById(R.id.btnEliminar).setOnClickListener(v -> deleteLibro());
    }

    /**
     * Configurar el spinner con los valores del estado de lectura.
     * Seleccionar el valor actual del libro
     */
    private void configurarSpinner() {

        // Crear un adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.estados_lectura,
                R.layout.spinner_item
        );

        // Establecer el diseño del elemento seleccionado
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstadoLectura.setAdapter(adapter);

        // Establecer el estado de lectura seleccionado
        spinnerEstadoLectura.setSelection(adapter.getPosition(getIntent().getStringExtra("estadoLectura")), false);
        estadoLecturaSeleccionado = getIntent().getStringExtra("estadoLectura");

        // Establecer el comportamiento del spinner
        spinnerEstadoLectura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Actualizar la variable con el estado de lectura seleccionado
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoLecturaSeleccionado = parent.getItemAtPosition(position).toString();
            }

            // No hacer nada si no se selecciona nada
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Guardar los cambios realizados al libro en la bd.
     * Validar los campos antes de actualizar
     */
    private void editarLibro(EditText etTitulo, EditText etAutor, EditText etPaginas, EditText etGenero, int idLibro) {
        // Obtener los datos del formulario
        String titulo = etTitulo.getText().toString().trim(), autor = etAutor.getText().toString().trim(),
                genero = etGenero.getText().toString().trim(), paginasStr = etPaginas.getText().toString().trim();

        // Validación de campos vacíos
        if (titulo.isEmpty() || autor.isEmpty() || paginasStr.isEmpty() || genero.isEmpty()) {
            showToast("Por favor, completa todos los campos");
            return;
        }

        // Conversión del campo numérico
        int paginas;
        try {
            paginas = Integer.parseInt(paginasStr);
        } catch (NumberFormatException e) {
            showToast("Número de páginas inválido");
            return;
        }

        // Validación del estado de lectura
        if (idLibro == -1) {
            showToast("Error: el ID del libro no es correcto");
            return;
        }

        // Operación en segundo plano para editar el libro
        executorService.execute(() -> {
            // Actualizar el libro en la bd
            AppDatabase.getInstance(this).libroDao().actualizar(new Libro(idLibro, titulo, autor, paginas, genero, estadoLecturaSeleccionado));

            // Actualizar la UI después de completar la operación
            runOnUiThread(() -> {
                showToast("Libro editado correctamente");
                // Volver a la actividad anterior
                setResult(RESULT_OK);
                // Terminar la actividad actual
                finish();
            });
        });
    }

    /**
     * Eliminar el libro actual de la base de datos segun el id
     */
    private void deleteLibro() {
        // Obtener el ID del libro desde el intent
        int idLibro = getIntent().getIntExtra("id", -1);
        // Validación del ID
        if (idLibro == -1) {
            showToast("Error: el id del libro es incorrecto");
            return;
        }

        // Operación en segundo plano que elimina el libro
        executorService.execute(() -> {
            // Eliminar el libro de la bd
            AppDatabase.getInstance(this).libroDao().eliminarPorId(idLibro);

            // Actualizar la UI después de completar la operación
            runOnUiThread(() -> {
                showToast("Libro eliminado correctamente");
                setResult(RESULT_OK);
                finish();
            });
        });
    }

    /**
     * Muestra un mensaje corto en pantalla.
     * @param message Texto del mensaje
     */
    private void showToast(String message) {
        // Mostrar un mensaje en pantalla
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
