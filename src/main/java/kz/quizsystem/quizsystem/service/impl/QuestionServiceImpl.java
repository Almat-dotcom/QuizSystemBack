package kz.quizsystem.quizsystem.service.impl;

import kz.quizsystem.quizsystem.dto.QuestionDTO;
import kz.quizsystem.quizsystem.mapper.QuestionMapper;
import kz.quizsystem.quizsystem.model.Questions;
import kz.quizsystem.quizsystem.model.Questions;
import kz.quizsystem.quizsystem.repository.QuestionRepository;
import kz.quizsystem.quizsystem.service.QuestionService;
import kz.quizsystem.quizsystem.service.QuestionService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Lazy
@Transactional
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDTO> getAll() {
        return questionMapper.toDTO(questionRepository.findAll());
    }

    @Override
    public QuestionDTO create(QuestionDTO dto) {
        if(dto==null){
            throw new RuntimeException("Please provide data with required info create Question");
        }
        if(dto.getId()!=null && dto.getId().trim().length()>0){
            throw new RuntimeException("You can't create new Question, because its already exists");
        }
        duplicatedCheck(dto);
        final Questions doc=questionMapper.toDocument(dto);
        return questionMapper.toDTO(questionRepository.save(doc));
    }

    public void duplicatedCheck(final  QuestionDTO dto){
        final Optional<Questions>optionalCategories=questionRepository.findByIdIsNotAndText(dto.getId(),dto.getText());
        if(optionalCategories.isPresent()){
            throw new RuntimeException("There is already that Question");
        }
    }

    @Override
    public QuestionDTO update(QuestionDTO dto) {
        if(dto==null){
            throw new RuntimeException("Please provide valid data to update Question");
        }
        if(dto.getId()==null|| !ObjectId.isValid(dto.getId())){
            throw new RuntimeException("Please provide valid data to update Question");
        }
        if(!questionRepository.existsById(dto.getId())){
            throw new RuntimeException("Question not found");
        }
        duplicatedCheck(dto);
        final Questions doc=questionMapper.toDocument(dto);
        return questionMapper.toDTO(questionRepository.save(doc));
    }

    @Override
    public void delete(String id) {
        if(id==null  || !ObjectId.isValid(id)){
            throw new RuntimeException("Please, provide valid Question to delete ");
        }
        if(!questionRepository.existsById(id)){
            throw new RuntimeException("Question not found");
        }
        questionRepository.deleteById(id);
    }

    @Override
    public Optional<QuestionDTO> get(String id) {
        if(id==null || !ObjectId.isValid(id)){
            throw new RuntimeException("Please, provide valid Question");
        }
        return questionRepository.findById(id).map(questionMapper::toDTO);
    }
}
