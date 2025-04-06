package com.example.proyecto_pmdm_ekaitz_jimenez.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.proyecto_pmdm_ekaitz_jimenez.dao.LibroDao;
import com.example.proyecto_pmdm_ekaitz_jimenez.models.Libro;

/**
 * Clase de la bd
 * Utiliza Room para las operaciones de la bd
 * COnfiguracion para trabajar con la entidad de libro
 */
@Database(entities = {Libro.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // DAO para acceder a las operaciones de la tabla de libros
    public abstract LibroDao libroDao();

    //instancia de la bd
    private static volatile AppDatabase instancia;

    /**
     * Obtener la instancia única de la bd
     * Si la instancia no existe, se crea
     *
     * @param context Contexto de la aplicacion.
     * @return La instanciia de la base de datos.
     */
    public static AppDatabase getInstance(Context context) {
        // comprueba si la instancia ya esta creada
        if (instancia == null) {
            // Bloque sincronizado para evitar crear más de una instancia en el entorno multihilo
            synchronized (AppDatabase.class) {
                // Si la instancia no se ha creado, la crea
                if (instancia == null) {
                    //construye la base de datos
                    instancia = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "libros_db")  // nombre de la base de datos
                            .fallbackToDestructiveMigration()  // Si falla la migración, destruye la bd y la recrea
                            .build();  // Crea la base de datos
                }
            }
        }
        return instancia;  // Devuelve la instancia de la bd
    }
}
