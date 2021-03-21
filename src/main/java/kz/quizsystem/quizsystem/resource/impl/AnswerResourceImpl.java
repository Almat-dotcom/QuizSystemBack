package kz.quizsystem.quizsystem.resource.impl;

import kz.quizsystem.quizsystem.dto.AnswerDTO;
import kz.quizsystem.quizsystem.resource.AnswerResource;
import kz.quizsystem.quizsystem.service.AnswerService;
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
public class AnswerResourceImpl implements AnswerResource {

    @Autowired
    private AnswerService answerService;

    @Override
    public ResponseEntity<List<AnswerDTO>> getAll() {
        return ResponseEntity.ok(answerService.getAll());
    }

    @Override
    public ResponseEntity<AnswerDTO> create(@Valid @RequestBody AnswerDTO dto) {
        final  AnswerDTO crDto=answerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crDto);
    }

    @Override
    public ResponseEntity<AnswerDTO> update(@Valid @RequestBody AnswerDTO dto) {
        return ResponseEntity.ok(answerService.update(dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        answerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AnswerDTO> get(@PathVariable("id") String id) {
        final AnswerDTO dto=answerService.get(id).orElseThrow(()->new RuntimeException("Can't find Answer"));
        return ResponseEntity.ok(dto);
    }
}
