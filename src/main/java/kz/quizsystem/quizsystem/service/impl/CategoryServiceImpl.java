package kz.quizsystem.quizsystem.service.impl;

import kz.quizsystem.quizsystem.dto.CategoryDTO;
import kz.quizsystem.quizsystem.mapper.CategoryMapper;
import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.repository.CategoryRepository;
import kz.quizsystem.quizsystem.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        return categoryMapper.toDTO(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        if(dto==null){
            throw new RuntimeException("Please provide data with required info create Category");
        }
        if(dto.getId()!=null && dto.getId().trim().length()>0){
            throw new RuntimeException("You can't create new Category, because its already exists");
        }
        duplicatedCheck(dto);
        final Categories doc=categoryMapper.toDocument(dto);
        return categoryMapper.toDTO(categoryRepository.save(doc));
    }

    public void duplicatedCheck(final  CategoryDTO dto){
        final Optional<Categories>optionalCategories=categoryRepository.findByIdIsNotAndName(dto.getId(),dto.getName());
        if(optionalCategories.isPresent()){
            throw new RuntimeException("There is already that Category");
        }
    }

    @Override
    public CategoryDTO update(CategoryDTO dto) {
        if(dto==null){
            throw new RuntimeException("Please provide valid data to update Category");
        }
        if(dto.getId()==null|| !ObjectId.isValid(dto.getId())){
            throw new RuntimeException("Please provide valid data to update Category");
        }
        if(!categoryRepository.existsById(dto.getId())){
            throw new RuntimeException("Category not found");
        }
        duplicatedCheck(dto);
        final Categories doc=categoryMapper.toDocument(dto);
        return categoryMapper.toDTO(categoryRepository.save(doc));
    }

    @Override
    public void delete(String id) {
        if(id==null  || !ObjectId.isValid(id)){
            throw new RuntimeException("Please, provide valid Category to delete ");
        }
        if(!categoryRepository.existsById(id)){
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<CategoryDTO> get(String id) {
        if(id==null || !ObjectId.isValid(id)){
            throw new RuntimeException("Please, provide valid Category");
        }
        return categoryRepository.findById(id).map(categoryMapper::toDTO);
    }
}
