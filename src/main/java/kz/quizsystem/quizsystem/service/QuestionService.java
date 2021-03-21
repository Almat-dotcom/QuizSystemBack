package kz.quizsystem.quizsystem.service;

import kz.quizsystem.quizsystem.dto.CategoryDTO;
import kz.quizsystem.quizsystem.dto.QuestionDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<QuestionDTO> getAll();

    QuestionDTO create(QuestionDTO dto);

    QuestionDTO update(QuestionDTO dto);

    void delete(String id);

    Optional<QuestionDTO> get(String id);
}
