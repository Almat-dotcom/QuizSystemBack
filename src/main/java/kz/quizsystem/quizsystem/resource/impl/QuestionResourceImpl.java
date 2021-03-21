package kz.quizsystem.quizsystem.resource.impl;

import kz.quizsystem.quizsystem.dto.QuestionDTO;
import kz.quizsystem.quizsystem.resource.CategoryResource;
import kz.quizsystem.quizsystem.resource.QuestionResource;
import kz.quizsystem.quizsystem.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionResourceImpl implements QuestionResource {

    @Autowired
    private QuestionService questionService;

    @Override
    public ResponseEntity<List<QuestionDTO>> getAll() {
        return ResponseEntity.ok(questionService.getAll());
    }

    @Override
    public ResponseEntity<QuestionDTO> create(@Valid @RequestBody QuestionDTO dto) {
        final  QuestionDTO crDto=questionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crDto);
    }

    @Override
    public ResponseEntity<QuestionDTO> update(@Valid @RequestBody QuestionDTO dto) {
        return ResponseEntity.ok(questionService.update(dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        questionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<QuestionDTO> get(@PathVariable("id") String id) {
        final QuestionDTO dto=questionService.get(id).orElseThrow(()->new RuntimeException("Can't find Question"));
        return ResponseEntity.ok(dto);
    }
}
