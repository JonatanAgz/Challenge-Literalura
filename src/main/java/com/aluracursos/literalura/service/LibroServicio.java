//package com.aluracursos.literalura.service;
//
//import com.aluracursos.literalura.model.Autor;
//import com.aluracursos.literalura.model.DatosAutor;
//import com.aluracursos.literalura.model.Libro;
//import com.aluracursos.literalura.model.DatosLibro;
//import com.aluracursos.literalura.repository.AutorRepositorio;
//import com.aluracursos.literalura.repository.LibroRepositorio;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class LibroServicio {
//
//    @Autowired
//    LibroRepositorio libroRepositorio;
//
//    @Autowired
//    AutorRepositorio autorRepositorio;
//
//    public Libro guardarLibro(DatosLibro datosLibro) {
//        // Obtener o crear el autor
//        DatosAutor datosAutor = datosLibro.autores().get(0);
//        Optional<Autor> autorExistente = autorRepositorio.findByNombre(datosAutor.nombre());
//
//        Autor autor;
//        if (autorExistente.isPresent()) {
//            autor = autorExistente.get();
//        } else {
//            autor = new Autor(null, datosAutor.nombre(), datosAutor.fechaNacimiento(), datosAutor.fechaDeceso(), null);
//            autor = autorRepositorio.save(autor);
//        }
//
//        Libro libro = new Libro( datosLibro.titulo(), datosLibro.lenguajes().get(0), datosLibro.totalDescargas(), autor);
//        autor.agregarLibro(libro);
//        autorRepositorio.save(autor);
//        return libroRepositorio.save(libro);
//    }
//
//    public List<Autor> obtenerAutoresConLibros() {
//        return autorRepositorio.findAll();
//    }
//}
