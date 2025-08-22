package BrandoACF.ExamenApi.Model.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

//DTO de autor, creo recordar que si no se especifica not null da error por lo que esta debidamente asignado junto con un mensaje de error
import java.util.Date;

@Getter
@Setter
public class AutorDTO {

    private Integer id;

    @NotNull(message = "Hay datos vacios")
    private String nombre;

    @NotNull(message = "Hay datos vacios")
    private String apellido;

    private String nacionalidad;

    private Date fechaNacimiento;

}