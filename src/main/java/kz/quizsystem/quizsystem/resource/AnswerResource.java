package kz.quizsystem.quizsystem.resource;

import kz.quizsystem.quizsystem.dto.AnswerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
public interface AnswerResource {
    @GetMapping(value = "/answers")
    ResponseEntity<List<AnswerDTO>> getAll();

    @PostMapping(value = "/answers",produces = "application/json")
    ResponseEntity<AnswerDTO> create(@Valid @RequestBody AnswerDTO dto);

    @PutMapping(value = "/answers")
    public ResponseEntity<AnswerDTO> update(@Valid @RequestBody AnswerDTO dto);

    @DeleteMapping(value = "/answers/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id);

    @GetMapping(value = "/answers/{id}")
    public ResponseEntity<AnswerDTO> get(@PathVariable("id") String id);
}
