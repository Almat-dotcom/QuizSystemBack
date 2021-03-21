package kz.quizsystem.quizsystem.resource;

import kz.quizsystem.quizsystem.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public interface CategoryResource {
    @GetMapping(value = "/categories")
    ResponseEntity<List<CategoryDTO>> getAll();

    @PostMapping(value = "/categories",produces = "application/json")
    ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto);

    @PutMapping(value = "/categories")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto);

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id);

    @GetMapping(value = "/categories/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable("id") String id);
}
