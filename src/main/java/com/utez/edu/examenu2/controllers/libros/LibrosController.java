package com.utez.edu.examenu2.controllers.libros;


import com.utez.edu.examenu2.models.libros.Libros;
import com.utez.edu.examenu2.services.libros.LibrosService;
import com.utez.edu.examenu2.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = {"*"})

public class LibrosController {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private LibrosService service;

    //get all
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Libros>>> getAll() {
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Libros>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    //insert
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Libros>> insert(@RequestBody Libros libros) {
        CustomResponse<Libros> response = service.insert(libros);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    //update
    @PutMapping("/update")
    public ResponseEntity<CustomResponse<Libros>> update(@RequestBody Libros libros) {
        CustomResponse<Libros> response = service.update(libros);
        if (response.getError()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteBookById(@PathVariable Long id) {
        CustomResponse<Boolean> response = service.deleteById(id);
        if (response.getError()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/sortedByReleaseDateDesc")
    public ResponseEntity<CustomResponse<List<Libros>>> getBooksSortedByReleaseDateDesc() {
        CustomResponse<List<Libros>> response = service.findBooksByReleaseDateDescending();

        if (response.getError()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
    }

    @GetMapping("/sortedByBooksDesc")
    public ResponseEntity<CustomResponse<List<Libros>>> getBooksSortedByAutorDesc() {
        CustomResponse<List<Libros>> response = service.findBooksByAutorDescending();

        if (response.getError()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
    }



}
