package kz.quizsystem.quizsystem.service.impl;

import kz.quizsystem.quizsystem.dto.TypeDTO;
import kz.quizsystem.quizsystem.mapper.TypeMapper;
import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Types;
import kz.quizsystem.quizsystem.repository.TypeRepository;
import kz.quizsystem.quizsystem.service.TypeService;
import kz.quizsystem.quizsystem.service.TypeService;
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
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TypeMapper typeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TypeDTO> getAll() {
        return typeMapper.toDTO(typeRepository.findAll());
    }

    @Override
    public TypeDTO create(TypeDTO dto) {
        if(dto==null){
            throw new RuntimeException("Please provide data with required info create Type");
        }
        if(dto.getId()!=null && dto.getId().trim().length()>0){
            throw new RuntimeException("You can't create new Type, because its already exists");
        }
        duplicatedCheck(dto);
        final Types doc=typeMapper.toDocument(dto);
        return typeMapper.toDTO(typeRepository.save(doc));    }

    public void duplicatedCheck(final  TypeDTO dto){
        final Optional<Types>optionalCategories=typeRepository.findByIdIsNotAndName(dto.getId(),dto.getName());
        if(optionalCategories.isPresent()){
            throw new RuntimeException("There is already that Type");
        }
    }

    @Override
    public TypeDTO update(TypeDTO dto) {
        if(dto==null){
            throw new RuntimeException("Please provide valid data to update Type");
        }
        if(dto.getId()==null|| !ObjectId.isValid(dto.getId())){
            throw new RuntimeException("Please provide valid data to update Type");
        }
        if(!typeRepository.existsById(dto.getId())){
            throw new RuntimeException("Type not found");
        }
        duplicatedCheck(dto);
        final Types doc=typeMapper.toDocument(dto);
        return typeMapper.toDTO(typeRepository.save(doc));
    }

    @Override
    public void delete(String id) {
        if(id==null  || !ObjectId.isValid(id)){
            throw new RuntimeException("Please, provide valid Type to delete ");
        }
        if(!typeRepository.existsById(id)){
            throw new RuntimeException("Type not found");
        }
        typeRepository.deleteById(id);
    }

    @Override
    public Optional<TypeDTO> get(String id) {
        if(id==null || !ObjectId.isValid(id)){
            throw new RuntimeException("Please, provide valid Type");
        }
        return typeRepository.findById(id).map(typeMapper::toDTO);
    }
}
