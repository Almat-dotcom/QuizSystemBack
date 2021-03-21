package kz.quizsystem.quizsystem.mapper;

import kz.quizsystem.quizsystem.dto.CategoryDTO;
import kz.quizsystem.quizsystem.dto.QuestionDTO;
import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Questions;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Questions toDocument(QuestionDTO questionDTO);

    QuestionDTO toDTO(Questions questions);

    List<Questions> toDocument(List<QuestionDTO> questionDTOList);

    List<QuestionDTO> toDTO(List<Questions> questionsList);
}
