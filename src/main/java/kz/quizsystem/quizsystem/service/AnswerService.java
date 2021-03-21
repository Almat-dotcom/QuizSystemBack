package kz.quizsystem.quizsystem.service;

import kz.quizsystem.quizsystem.dto.AnswerDTO;
import kz.quizsystem.quizsystem.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    List<AnswerDTO> getAll();

    AnswerDTO create(AnswerDTO dto);

    AnswerDTO update(AnswerDTO dto);

    void delete(String id);

    Optional<AnswerDTO> get(String id);
}
