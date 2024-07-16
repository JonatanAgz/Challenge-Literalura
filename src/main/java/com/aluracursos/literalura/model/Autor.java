package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaDeceso;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    // Constructor por defecto
    public Autor() {
    }

    // Constructor con argumentos
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaDeceso = datosAutor.fechaDeceso();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaDeceso() {
        return fechaDeceso;
    }

    public void setFechaDeceso(Integer fechaDeceso) {
        this.fechaDeceso = fechaDeceso;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

//    public void adicionarLibros(List<Libro> libros){
//        libros.add()
//    }


//
//    public void agregarLibro(Libro libro){
//        libros.add(libro);
//        libro.setAutor(this);
//    }
//
//    public void retirarLibro(Libro libro){
//        libros.remove(libro);
//        libro.setAutor(null);
//    }

    @Override
    public String toString() {
        String listaLibros = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(" | "));
        return  "******* Autor ******* \n" +
                "Nombre: " + nombre + "\n" +
                "Año de nacimiento: " + fechaNacimiento + "\n" +
                "Año de deceso: " + fechaDeceso + "\n" +
                "Libros: " + listaLibros + "\n" +
                "********************* \n";
    }
}
