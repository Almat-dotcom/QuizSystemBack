package kz.quizsystem.quizsystem.repository;

import kz.quizsystem.quizsystem.model.Answers;
import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Types;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AnswerRepository extends MongoRepository<Answers,String> {
}
