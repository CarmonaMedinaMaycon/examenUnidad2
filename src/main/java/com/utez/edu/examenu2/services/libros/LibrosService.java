package com.utez.edu.examenu2.services.libros;


import com.utez.edu.examenu2.models.libros.Libros;
import com.utez.edu.examenu2.models.libros.LibrosRepository;
import com.utez.edu.examenu2.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibrosService {

    @Autowired
    private LibrosRepository repository;


    //insert
    public CustomResponse<Libros> insert(Libros libros) {
        return new CustomResponse<>(
                this.repository.saveAndFlush(libros),
                false,
                200,
                "libro registrado"
        );
    }

    //get all
    public CustomResponse<List<Libros>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

    //get one
    public CustomResponse<Libros> getOne(Long id) {
        Optional<Libros> movies = repository.findById(id);
        if (movies.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El libro no existe."
            );
        }
        return new CustomResponse<>(
                movies.get(),
                false,
                200,
                "Ok"
        );
    }

    //update
    public CustomResponse<Libros> update(Libros libros) {
        if (!this.repository.existsById(libros.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El libro no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(libros),
                false,
                200,
                "Película actualizada"
        );
    }

    //delete by id

    public CustomResponse<Boolean> deleteById(Long id) {
        if (!this.repository.existsById(id)) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La película no existe"
            );
        }

        this.repository.deleteById(id);

        return new CustomResponse<>(
                true,
                false,
                200,
                "Libro eliminado exitosamente"
        );
    }

    public CustomResponse<List<Libros>> findBooksByReleaseDateDescending() {
        List<Libros> libros = repository.findByOrderByReleaseDateDesc();
        if (libros.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No se encontraron libros"
            );
        } else {
            return new CustomResponse<>(
                    libros,
                    false,
                    200,
                    "Libros encontradas"
            );
        }
    }

    public CustomResponse<List<Libros>> findBooksByAutorDescending() {
        List<Libros> libros = repository.findByOrderByAutorDesc();
        if (libros.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No se encontraron libros"
            );
        } else {
            return new CustomResponse<>(
                    libros,
                    false,
                    200,
                    "Libros encontradas"
            );
        }
    }


}
