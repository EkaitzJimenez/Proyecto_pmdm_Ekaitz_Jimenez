package com.example.proyecto_pmdm_ekaitz_jimenez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_pmdm_ekaitz_jimenez.ui.home.HomeFragment;

/**
 * Actividad principal:
 * Gestionar la vista inicial y carga el fragmento de inicio.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Método que se ejecuta al crear la actividad.
     * Configrar el modo oscuro y cargar el fragmento principal (HomeFragment)
     * @param savedInstanceState El estado guardado de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamar al metodo onCreate de la superclase
        super.onCreate(savedInstanceState);

        // Establecer el modo claro para la aplicacion (desactivar el modo oscuro)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Establecer el layout de la activida
        setContentView(R.layout.activity_main);

        // Configuración de los márgenes de la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Si no se ha guardado el estado de la actividad, caarga el fragmento de inicio
        if (savedInstanceState == null) {
            // Cargar el fragmento de inicio
            getSupportFragmentManager()
                    .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())  // Reemplaza el contenedor de fragmentos con HomeFragment
                    .commit();  // Ejecutar la transacción
        }
    }
}
