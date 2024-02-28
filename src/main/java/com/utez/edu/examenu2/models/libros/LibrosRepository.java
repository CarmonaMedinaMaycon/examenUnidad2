package com.utez.edu.examenu2.models.libros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibrosRepository extends JpaRepository <Libros, Long> {


    List<Libros> findByOrderByReleaseDateDesc();

    List<Libros> findByOrderByAutorDesc();

}
