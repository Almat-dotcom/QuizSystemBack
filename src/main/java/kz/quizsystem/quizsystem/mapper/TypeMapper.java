package kz.quizsystem.quizsystem.mapper;

import kz.quizsystem.quizsystem.dto.TypeDTO;
import kz.quizsystem.quizsystem.model.Types;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    Types toDocument(TypeDTO bookDTO);

    TypeDTO toDTO(Types book);

    List<Types> toDocument(List<TypeDTO> bookDTOList);

    List<TypeDTO> toDTO(List<Types> bookList);
}
