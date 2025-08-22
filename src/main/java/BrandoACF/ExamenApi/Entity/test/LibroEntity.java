package BrandoACF.ExamenApi.Entity.test;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "LIBROS")
public class LibroEntity {

    //Entity, al igual que el dto es una forma de "almacenar" los campos de los libros en la api, notese que @Column resalta donde inician los campos
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "anio_publicacion")
    private Integer anio_publicacion;

    @Column(name = "genero")
    private String genero;

    @Column(name = "autor_id")
    private Integer autor_id;
}