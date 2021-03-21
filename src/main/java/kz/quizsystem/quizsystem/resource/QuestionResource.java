package kz.quizsystem.quizsystem.resource;

import kz.quizsystem.quizsystem.dto.QuestionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
public interface QuestionResource {
    @GetMapping(value = "/questions")
    ResponseEntity<List<QuestionDTO>> getAll();

    @PostMapping(value = "/questions",produces = "application/json")
    ResponseEntity<QuestionDTO> create(@Valid @RequestBody QuestionDTO dto);

    @PutMapping(value = "/questions")
    public ResponseEntity<QuestionDTO> update(@Valid @RequestBody QuestionDTO dto);

    @DeleteMapping(value = "/questions/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id);

    @GetMapping(value = "/questions/{id}")
    public ResponseEntity<QuestionDTO> get(@PathVariable("id") String id);
}
