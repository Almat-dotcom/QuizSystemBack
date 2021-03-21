package kz.quizsystem.quizsystem.mapper;

import kz.quizsystem.quizsystem.dto.CategoryDTO;
import kz.quizsystem.quizsystem.dto.TypeDTO;
import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Types;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Categories toDocument(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Categories categories);

    List<Categories> toDocument(List<CategoryDTO> categoryDTOList);

    List<CategoryDTO> toDTO(List<Categories> categoriesList);
}
