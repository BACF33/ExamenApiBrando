package BrandoACF.ExamenApi.Service.test;

import BrandoACF.ExamenApi.Entity.test.AutorEntity;
import BrandoACF.ExamenApi.Model.DTO.AutorDTO;
import BrandoACF.ExamenApi.Repository.test.AutorRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AutorService {

    @Autowired
    private AutorRepository repo;

    //Funcion para obtener los dtos y organizarlos en una lista para posteriormente hacer return con esta
    public List<AutorDTO> getAllAutores(){
        List<AutorEntity> autor = repo.findAll();
        return autor.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Funcion a ser llamada por el controller para la C de CRUD
    public AutorDTO insert(@Valid AutorDTO jsonData) {
        if (jsonData == null){
            //Mensaje advertencia si hay un ator vacio
            throw new IllegalArgumentException("Autor vacio");
        }
        try {
            AutorEntity autorEntity = convertirAEntity(jsonData);
            AutorEntity autorSave = repo.save(autorEntity);
            return convertirADTO(autorSave);
        }catch (Exception e){
            log.error("Error: " + e.getMessage());
            throw new BrandoACF.ExamenApi.Exceptions.test.Exception404("Error " + e.getMessage());
        }
    }

    public AutorDTO update(Long id, @Valid AutorDTO jsonDTO) {
        if (jsonDTO == null){
            throw new IllegalArgumentException("Autor vacio");
        }
        AutorEntity autorData = repo.findById(id).orElseThrow(()-> new BrandoACF.ExamenApi.Exceptions.test.Exception404("autor no encontrado"));
        autorData.setNombre(jsonDTO.getNombre());
        autorData.setApellido(jsonDTO.getApellido());
        autorData.setNacionalidad(jsonDTO.getNacionalidad());
        autorData.setFechaNacimiento(jsonDTO.getFechaNacimiento());
        AutorEntity autorUpdate = repo.save(autorData);
        return convertirADTO(autorUpdate);
    }

    public boolean delete(Long id) {
        try{
            AutorEntity objEntity = repo.findById(id).orElse(null);
            if (objEntity != null){
                repo.deleteById(id);
                return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontró el autor "+id,1);
        }
    }

    private AutorDTO convertirADTO(AutorEntity objEntity) {
        AutorDTO dto = new AutorDTO();
        dto.setId(objEntity.getId());
        dto.setNombre(objEntity.getNombre());
        dto.setApellido(objEntity.getApellido());
        dto.setNacionalidad(objEntity.getNacionalidad());
        dto.setFechaNacimiento(objEntity.getFechaNacimiento());
        return dto;
    }

    private AutorEntity convertirAEntity(@Valid AutorDTO json) {
        AutorEntity objEntity = new AutorEntity();
        objEntity.setNombre(json.getNombre());
        objEntity.setApellido(json.getApellido());
        objEntity.setNacionalidad(json.getNacionalidad());
        objEntity.setFechaNacimiento(json.getFechaNacimiento());
        return objEntity;
    }
}