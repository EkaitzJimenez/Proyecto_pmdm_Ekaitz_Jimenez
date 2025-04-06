package com.example.proyecto_pmdm_ekaitz_jimenez;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import android.content.Context;

import com.example.proyecto_pmdm_ekaitz_jimenez.dao.LibroDao;
import com.example.proyecto_pmdm_ekaitz_jimenez.database.AppDatabase;
import com.example.proyecto_pmdm_ekaitz_jimenez.models.Libro;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LibroDaoTest {

    private AppDatabase db;
    private LibroDao libroDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        libroDao = db.libroDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertarLibro() {
        Libro libro = new Libro("El Ekaitz", "Ekaitz Jimenez", 208, "Ficción", "Leído");
        libroDao.insertar(libro);
        List<Libro> libros = libroDao.obtenerTodos();
        assertEquals(1, libros.size());
        Libro libroRecuperado = libros.get(0);
        assertEquals("El Ekaitz", libroRecuperado.getTitulo());
    }

    @Test
    public void eliminarLibro() {
        Libro libro = new Libro("El Ekaitz", "Ekaitz Jimenez", 208, "Ficción", "Leído");
        libroDao.insertar(libro);
        List<Libro> librosInsertados = libroDao.obtenerTodos();
        Libro libroInsertado = librosInsertados.get(0);
        int libroId = libroInsertado.getId();
        libroDao.eliminarPorId(libroId);
        List<Libro> libros = libroDao.obtenerTodos();
        assertTrue(libros.isEmpty());
    }

    @Test
    public void actualizarLibro() {
        Libro libro = new Libro("El Ekaitz", "Ekaitz Jimenez", 208, "Ficción", "Leído");
        libroDao.insertar(libro);
        List<Libro> librosInsertados = libroDao.obtenerTodos();
        Libro libroInsertado = librosInsertados.get(0);
        Libro libroActualizado = new Libro(libroInsertado.getId(), "El Ekaitz", "Ekaitz Jimenez", 208, "Ficción", "No leído");
        libroDao.actualizar(libroActualizado);
        assertEquals(libroActualizado.getEstadoLectura(), libroDao.obtenerTodos().get(0).getEstadoLectura());
    }
}
