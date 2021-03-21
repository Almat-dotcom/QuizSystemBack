package kz.quizsystem.quizsystem.service;

import kz.quizsystem.quizsystem.dto.CategoryDTO;
import kz.quizsystem.quizsystem.dto.TypeDTO;

import java.util.List;
import java.util.Optional;

public interface TypeService {
    List<TypeDTO> getAll();

    TypeDTO create(TypeDTO dto);

    TypeDTO update(TypeDTO dto);

    void delete(String id);

    Optional<TypeDTO> get(String id);
}
