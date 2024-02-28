package com.utez.edu.examenu2.models.libros;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Text;

import java.util.Base64;
import java.util.Date;

@Entity
@Table(name = "libros")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String autor;
    @Column(nullable = false)
    private byte[] image;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) // solo guarda año/mes/dia yyyy-mm-dd
    private Date releaseDate; //

}
