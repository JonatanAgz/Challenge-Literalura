package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Lenguaje;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    Libro findByTitulo(String titulo);

//    @Query("SELECT l FROM Libros l WHERE :codigo ILIKE %:l.lenguaje%")
    List<Libro> findByLenguaje(Lenguaje codigo);
}
