package kz.quizsystem.quizsystem.repository;

import kz.quizsystem.quizsystem.model.Categories;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Categories,String> {
    Optional<Categories> findByIdIsNotAndName(String id, String name);
}
