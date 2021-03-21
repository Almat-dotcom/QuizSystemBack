package kz.quizsystem.quizsystem.resource;

import kz.quizsystem.quizsystem.dto.TypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
public interface TypeResource {
    @GetMapping(value = "/types")
    ResponseEntity<List<TypeDTO>> getAll();

    @PostMapping(value = "/types",produces = "application/json")
    ResponseEntity<TypeDTO> create(@Valid @RequestBody TypeDTO dto);

    @PutMapping(value = "/types")
    public ResponseEntity<TypeDTO> update(@Valid @RequestBody TypeDTO dto);

    @DeleteMapping(value = "/types/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id);

    @GetMapping(value = "/types/{id}")
    public ResponseEntity<TypeDTO> get(@PathVariable("id") String id);
}
