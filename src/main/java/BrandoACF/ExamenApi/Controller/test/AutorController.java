package BrandoACF.ExamenApi.Controller.test;

import BrandoACF.ExamenApi.Model.DTO.AutorDTO;
import BrandoACF.ExamenApi.Service.test.AutorService;
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
@RequestMapping("/apiAutores")
@CrossOrigin
public class AutorController {

    @Autowired
    private AutorService service;

    //Endpoint para obtener autores
    @GetMapping("/getDataAutores")
    private ResponseEntity<?> getData(){
        try {
            List<AutorDTO> autores = service.getAllAutores();
            return ResponseEntity.ok(autores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Error",  e.getMessage()));
        }
    }

    //Endpoint para creat autores
    @PostMapping("/newAutor")
    private ResponseEntity<Map<String, Object>> inserCategory(@Valid @RequestBody AutorDTO json, HttpServletRequest request){
        try{
            AutorDTO response =service.insert(json);

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

    //Endpoint para actualizar el autor con la id definida
    @PutMapping("/updateAutor/{id}")
    public ResponseEntity<?> modificarAutor(
            @PathVariable Long id,
            @Valid @RequestBody AutorDTO autor,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            AutorDTO autorActualizado = service.update(id, autor);
            return ResponseEntity.ok(autorActualizado);
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

    //Endpoint para eliminar el autor con la id definida
    @DeleteMapping("/deleteAutor/{id}")
    public ResponseEntity<Map<String, Object>> eliminarAutor(@PathVariable Long id) {
        try {
            if (!service.delete(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "Autor no encontrada")
                        .body(Map.of(
                                "error", "404"
                        ));
            }


            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado"
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error"
            ));
        }
    }
}