package kz.quizsystem.quizsystem.repository;

import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Types;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TypeRepository extends MongoRepository<Types,String> {
    Optional<Types> findByIdIsNotAndName(String id, String name);
}
