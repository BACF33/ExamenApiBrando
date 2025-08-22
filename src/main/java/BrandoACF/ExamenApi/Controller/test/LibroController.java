package BrandoACF.ExamenApi.Controller.test;

import BrandoACF.ExamenApi.Model.DTO.LibroDTO;
import BrandoACF.ExamenApi.Service.test.LibroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiLibros")
@CrossOrigin
public class LibroController {

    @Autowired
    private LibroService service;

    //Endpoint para obtener libros
    @GetMapping("/getDataLibros")
    private ResponseEntity<?> getData(){
        //Bloque try catch que de fallar mostrara un error e.getMessage
        try {
            List<LibroDTO> libros = service.getAllLibros();
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Error",  e.getMessage()));
        }
    }

    //Endpoint para crear un nuevo libro
    @PostMapping("/newLibro")
    private ResponseEntity<Map<String, Object>> inserCategory(@Valid @RequestBody LibroDTO json, HttpServletRequest request){
        try{
            LibroDTO response =service.insert(json);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "Estado", "Completado"
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "Error", e.getMessage()
                    ));
        }
    }

    //Endpoint para actualizar un libro con la id definida
    @PutMapping("/updateLibro/{id}")
    public ResponseEntity<?> modificarLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibroDTO libro,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        //Uso de excepciones en caso de que el try falle, la segunda mostrara el campo duplicado en si
        try{
            LibroDTO libroActualizado = service.update(id, libro);
            return ResponseEntity.ok(libroActualizado);
        }
        catch (BrandoACF.ExamenApi.Exceptions.test.Exception404 e){
            return ResponseEntity.notFound().build();
        }
        catch (BrandoACF.ExamenApi.Exceptions.test.ExceptionDuplicada e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados","campo", e.getColumnDuplicate())
            );
        }
    }

    //Endpoint para eliminar un libro con la id definida
    @DeleteMapping("/deleteLibro/{id}")
    public ResponseEntity<Map<String, Object>> eliminarAutor(@PathVariable Long id) {
        try {
            if (!service.delete(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        //Mensahe de errir eb casi de que el catch falle (notese el ! a la izquierda de service.delete)
                        .header("X-Mensaje-Error", "Libro no encontrado")
                        .body(Map.of(
                                "error", "404"
                        ));
            }


            return ResponseEntity.ok().body(Map.of(
                    "status", "No error"
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error"
            ));
        }
    }
}