package BrandoACF.ExamenApi.Repository.test;

import BrandoACF.ExamenApi.Entity.test.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//Importacion de """libreria""" al proyecto con abstracciones para el crud
public interface AutorRepository extends JpaRepository<AutorEntity, Long> {
}