package kz.quizsystem.quizsystem.dto;

import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Types;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO implements Serializable {
    private String id;

    @NotBlank
    private Boolean isActive;

    @NotBlank
    private String text;

    @NotBlank
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    private Types types;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categories categories;
}
