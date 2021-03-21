package kz.quizsystem.quizsystem.mapper;

import kz.quizsystem.quizsystem.dto.AnswerDTO;
import kz.quizsystem.quizsystem.dto.QuestionDTO;
import kz.quizsystem.quizsystem.model.Answers;
import kz.quizsystem.quizsystem.model.Questions;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answers toDocument(AnswerDTO answerDTO);

    AnswerDTO toDTO(Answers answers);

    List<Answers> toDocument(List<AnswerDTO> answerDTOList);

    List<AnswerDTO> toDTO(List<Answers> answersList);
}
