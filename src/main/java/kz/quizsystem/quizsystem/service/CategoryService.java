package kz.quizsystem.quizsystem.service;

import kz.quizsystem.quizsystem.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO create(CategoryDTO dto);

    CategoryDTO update(CategoryDTO dto);

    void delete(String id);

    Optional<CategoryDTO> get(String id);
}
