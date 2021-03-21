package kz.quizsystem.quizsystem.resource.impl;

import kz.quizsystem.quizsystem.dto.CategoryDTO;
import kz.quizsystem.quizsystem.resource.CategoryResource;
import kz.quizsystem.quizsystem.service.CategoryService;
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
public class CategoryResourceImpl implements CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @Override
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) {
        final CategoryDTO crDto=categoryService.create(dto);
        System.out.println(dto);
        System.out.println(crDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crDto);
    }

    @Override
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.update(dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CategoryDTO> get(@PathVariable("id") String id) {
        final CategoryDTO dto=categoryService.get(id).orElseThrow(()->new RuntimeException("Can't find Category"));
        return ResponseEntity.ok(dto);
    }
}
