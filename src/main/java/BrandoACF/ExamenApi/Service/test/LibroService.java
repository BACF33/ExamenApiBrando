package BrandoACF.ExamenApi.Service.test;

import BrandoACF.ExamenApi.Entity.test.LibroEntity;
import BrandoACF.ExamenApi.Model.DTO.LibroDTO;
import BrandoACF.ExamenApi.Repository.test.LibroRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibroService {

    @Autowired
    private LibroRepository repo;

    //Funcion para obtener los dtos y organizarlos en una lista para posteriormente hacer return con esta
    public List<LibroDTO> getAllLibros(){
        //Uso de la interface repository para obtener todos los libros
        List<LibroEntity> libro = repo.findAll();
        return libro.stream()
                //Necesario convertir a dto pues son otros tipos de datos no compatibles
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Funcion a ser llamada por el controller para la C de CRUD
    public LibroDTO insert(@Valid LibroDTO jsonData) {
        if (jsonData == null){
            //Uso de la excepcion 404
            throw new IllegalArgumentException("El libro no puede ser blanco");
        }
        try {
            //Conversion a entity para poder ser procesados (autorSave) posteriormente se guarda con ayuda de jpa para la posterior insercion en sql en si (en modo dto)
            LibroEntity autorEntity = convertirAEntity(jsonData);
            LibroEntity autorSave = repo.save(autorEntity);
            return convertirADTO(autorSave);
        }catch (Exception e){
            log.error("Error al registrar al autor: " + e.getMessage());
            //Uso de la excepcion 404 en el contecto de la C
            throw new BrandoACF.ExamenApi.Exceptions.test.Exception404("Error al registrar el libro: " + e.getMessage());
        }
    }

    public LibroDTO update(Long id, @Valid LibroDTO jsonDTO) {
        if (jsonDTO == null){
            throw new IllegalArgumentException("El autor no puede ir en blanco");
        }
        //Verificar existencia
        LibroEntity libroData = repo.findById(id).orElseThrow(()-> new BrandoACF.ExamenApi.Exceptions.test.Exception404("libro no encontrado"));
        //Actualizar campos (si existe)
        libroData.setTitulo(jsonDTO.getTitulo());
        libroData.setIsbn(jsonDTO.getIsbn());
        libroData.setAnio_publicacion(jsonDTO.getAnio_publicacion());
        libroData.setGenero(jsonDTO.getGenero());
        libroData.setAutor_id(jsonDTO.getAutor_id());
        LibroEntity libroUpdate = repo.save(libroData);
        //Retornar (como DTO)
        return convertirADTO(libroUpdate);
    }

    public boolean delete(Long id) {
        try{
            LibroEntity objEntity = repo.findById(id).orElse(null);
            if (objEntity != null){
                //Uso de la ayuda de jpa para eliminar el campo con la id insertada
                repo.deleteById(id);
                return true;
            }
            return false;
            //En caso de error por parte del try imprimiermos usaremos el catch el cual puede dar informacion relevante
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontró el autor"+id,1);
        }
    }

    private LibroDTO convertirADTO(LibroEntity objEntity) {
        //Convertir uno por uno los campos de entity a dto
        LibroDTO dto = new LibroDTO();
        dto.setId(objEntity.getId());
        dto.setTitulo(objEntity.getTitulo());
        dto.setIsbn(objEntity.getIsbn());
        dto.setAnio_publicacion(objEntity.getAnio_publicacion());
        dto.setGenero(objEntity.getGenero());
        dto.setAutor_id(objEntity.getAutor_id());
        //Retornar el dto a una potencial funcion que la necesite
        return dto;
    }

    private LibroEntity convertirAEntity(@Valid LibroDTO json) {
        //Convertir uno por uno los campos de dto a entity
        LibroEntity objEntity = new LibroEntity();
        objEntity.setTitulo(json.getTitulo());
        objEntity.setIsbn(json.getIsbn());
        objEntity.setAnio_publicacion(json.getAnio_publicacion());
        objEntity.setGenero(json.getGenero());
        objEntity.setAutor_id(json.getAutor_id());
        //Retornar el entity a una potencial funcion que la necesite
        return objEntity;
    }
}