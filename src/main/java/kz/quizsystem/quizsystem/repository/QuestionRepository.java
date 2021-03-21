package kz.quizsystem.quizsystem.repository;

import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Questions;
import kz.quizsystem.quizsystem.model.Types;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface QuestionRepository extends MongoRepository<Questions,String> {
    Optional<Questions> findByIdIsNotAndText(String id, String text);

}
