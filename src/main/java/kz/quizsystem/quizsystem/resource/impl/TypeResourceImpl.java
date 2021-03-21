package kz.quizsystem.quizsystem.resource.impl;

import kz.quizsystem.quizsystem.dto.TypeDTO;
import kz.quizsystem.quizsystem.resource.TypeResource;
import kz.quizsystem.quizsystem.service.TypeService;
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
public class TypeResourceImpl implements TypeResource {

    @Autowired
    private TypeService typeService;

    @Override
    public ResponseEntity<List<TypeDTO>> getAll() {
        return ResponseEntity.ok(typeService.getAll());
    }

    @Override
    public ResponseEntity<TypeDTO> create(@Valid @RequestBody TypeDTO dto) {
        final  TypeDTO crDto=typeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crDto);
    }

    @Override
    public ResponseEntity<TypeDTO> update(@Valid @RequestBody TypeDTO dto) {
        return ResponseEntity.ok(typeService.update(dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        typeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TypeDTO> get(@PathVariable("id") String id) {
        final TypeDTO dto=typeService.get(id).orElseThrow(()->new RuntimeException("Can't find Type"));
        return ResponseEntity.ok(dto);
    }
}
