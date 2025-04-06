package com.example.proyecto_pmdm_ekaitz_jimenez.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Clase de Libro.
 * Esta clase se usa por Room para mapear los libros
 */
@Entity(tableName = "libros")
public class Libro {

    // Identificador único del libro
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Título del libro
    private String titulo;

    // Autor del libro
    private String autor;

    // Número de páginas del libro
    private int paginas;

    // Género del libro
    private String genero;

    // Reseña del libro (sin uso)
    private String resena;

    // Estado de lectura del libro
    private String estadoLectura;

    /**
     * Constructor vacio para Room
     */
    public Libro() {
    }

    /**
     * Constructor para crear un libro sin id.
     * Se usa para crear nuevos libros.
     *
     * @param titulo título
     * @param autor autor
     * @param paginas número de páginas
     * @param genero género
     * @param estadoLectura estado de lectura
     */
    @Ignore
    public Libro(String titulo, String autor, int paginas, String genero, String estadoLectura) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.genero = genero;
        this.estadoLectura = estadoLectura;
    }

    /**
     * Constructor para actualizar un libro con un id concreto
     *
     * @param id id del libro, se usa para actualizar el registro existente
     * @param titulo título
     * @param autor autor
     * @param paginas número de páginas
     * @param genero género
     * @param estadoLectura stado de lectura
     */
    public Libro(int id, String titulo, String autor, int paginas, String genero, String estadoLectura) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.genero = genero;
        this.estadoLectura = estadoLectura;
    }

    // Getters y Setters

    /**
     * Obtener id del libro
     *
     * @return id del libro
     */
    public int getId() {
        return id;
    }

    /**
     * Establecer id del libro
     *
     * @param id id del libro
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtener título del libro
     *
     * @return título del libro
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * establecer título del libro
     *
     * @param titulo título del libro
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtener autor del libro
     *
     * @return autor del libro
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Establecer autor del libro
     *
     * @param autor autor del libro
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     *Obtener número de páginas del libro
     *
     * @return número de páginas del libro
     */
    public int getPaginas() {
        return paginas;
    }

    /**
     * Establecer número de páginas del libro
     *
     * @param paginas número de páginas del libro
     */
    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    /**
     * Obtener género del libro
     *
     * @return género del libro
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Establecer género del libro
     *
     * @param genero género del libro
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtener reseña del libro
     *
     * @return reseña del libro
     */
    public String getResena() {
        return resena;
    }

    /**
     * Establecer reseña del libro
     *
     * @param resena reseña del libro
     */
    public void setResena(String resena) {
        this.resena = resena;
    }

    /**
     * Obtener estado de lectura del libro
     *
     * @return estado de lectura del libro
     */
    public String getEstadoLectura() {
        return estadoLectura;
    }

    /**
     * Establecer estado de lectura del libro
     *
     * @param estadoLectura estado de lectura del libro
     */
    public void setEstadoLectura(String estadoLectura) {
        this.estadoLectura = estadoLectura;
    }
}
