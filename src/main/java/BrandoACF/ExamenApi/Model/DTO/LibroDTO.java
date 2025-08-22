package BrandoACF.ExamenApi.Model.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

//DTO de libro, creo recordar que si no se especifica not null da error por lo que esta debidamente asignado junto con un mensaje de error
@Getter
@Setter
public class LibroDTO {

    private Integer id;

    @NotNull(message = "Hay datos vacios")
    private String titulo;

    @NotNull(message = "Hay datos vacios")
    private String isbn;

    private Integer anio_publicacion;

    private String genero;

    private Integer autor_id;
}