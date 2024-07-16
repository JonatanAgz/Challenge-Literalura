package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

    @Query("SELECT a FROM Autor a WHERE :fecha >= a.fechaNacimiento AND (:fecha <= a.fechaDeceso OR a.fechaDeceso IS NULL)")
    List<Autor> findByFecha(Integer fecha);
}
