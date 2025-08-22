package BrandoACF.ExamenApi.Repository.test;

import BrandoACF.ExamenApi.Entity.test.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//Importacion de """libreria""" al proyecto con abstracciones para el crud
public interface LibroRepository extends JpaRepository<LibroEntity, Long> {
}