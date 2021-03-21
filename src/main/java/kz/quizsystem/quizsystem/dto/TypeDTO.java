package kz.quizsystem.quizsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDTO implements Serializable {
    private String id;

    @NotBlank
    private String name;
}
