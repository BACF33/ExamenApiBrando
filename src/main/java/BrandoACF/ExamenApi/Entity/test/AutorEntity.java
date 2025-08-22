package BrandoACF.ExamenApi.Entity.test;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "AUTORES")
public class AutorEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "NACIONALIDAD")
    private String nacionalidad;

    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;
}