package kz.quizsystem.quizsystem.service.impl;

import kz.quizsystem.quizsystem.dto.AnswerDTO;
import kz.quizsystem.quizsystem.mapper.AnswerMapper;
import kz.quizsystem.quizsystem.model.Answers;
import kz.quizsystem.quizsystem.repository.AnswerRepository;
import kz.quizsystem.quizsystem.service.AnswerService;
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
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AnswerDTO> getAll() {
        return answerMapper.toDTO(answerRepository.findAll());
    }

    @Override
    public AnswerDTO create(AnswerDTO dto) {
        if(dto==null){
            throw new RuntimeException("Please provide data with required info create Answer");
        }
        if(dto.getId()!=null && dto.getId().trim().length()>0){
            throw new RuntimeException("You can't create new Answer, because its already exists");
        }
        final Answers doc=answerMapper.toDocument(dto);
        return answerMapper.toDTO(answerRepository.save(doc));    }

    @Override
    public AnswerDTO update(AnswerDTO dto) {
        if(dto==null){
            throw new RuntimeException("Please provide valid data to update Answer");
        }
        if(dto.getId()==null|| !ObjectId.isValid(dto.getId())){
            throw new RuntimeException("Please provide valid data to update Answer");
        }
        if(!answerRepository.existsById(dto.getId())){
            throw new RuntimeException("Answer not found");
        }
        final Answers doc=answerMapper.toDocument(dto);
        return answerMapper.toDTO(answerRepository.save(doc));
    }

    @Override
    public void delete(String id) {
        if(id==null  || !ObjectId.isValid(id)){
            throw new RuntimeException("Please, provide valid Answer to delete ");
        }
        if(!answerRepository.existsById(id)){
            throw new RuntimeException("Answer not found");
        }
        answerRepository.deleteById(id);
    }

    @Override
    public Optional<AnswerDTO> get(String id) {
        if(id==null || !ObjectId.isValid(id)){
            throw new RuntimeException("Please, provide valid Answer");
        }
        return answerRepository.findById(id).map(answerMapper::toDTO);
    }
}
