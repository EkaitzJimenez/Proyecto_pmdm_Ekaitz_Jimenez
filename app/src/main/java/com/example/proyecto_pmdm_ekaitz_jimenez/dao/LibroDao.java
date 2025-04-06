package com.example.proyecto_pmdm_ekaitz_jimenez.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyecto_pmdm_ekaitz_jimenez.models.Libro;


import java.util.List;

/**
 * Interfaz DAO(Data Access Object) para operaciones CRUD sobre entidad de Libro
 * Tiene los métodos para interactuar con la bd de libros.
 */
@Dao
public interface LibroDao {

    /**
     * Insertar un nuevo libro en la bd
     *
     * @param libro objeto libro a insertarse en la bd
     */
    @Insert
    void insertar(Libro libro);

    /**
     * Eliminar un libro de la bd por el id
     *
     *@param idLibro ID del libro que se va a eliminar
     */
    @Query("DELETE FROM libros WHERE id = :idLibro")
    void eliminarPorId(int idLibro);

    /**
     * Obtener todos los libros almacenados en la bd
     *
     * @return lista de todos los libros
     */
    @Query("SELECT * FROM libros")
    List<Libro> obtenerTodos();

    /**
     * Actuatizar la información de un librode la bd
     * El libro tiene el mismo ID que el libro en la base de datos para ser actualizado.
     *
     * @param libroEditado El libro con nuevos datos que se van a actualizar.
     */
    @Update
    void actualizar(Libro libroEditado);
}
