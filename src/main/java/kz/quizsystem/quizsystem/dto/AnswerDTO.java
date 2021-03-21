package kz.quizsystem.quizsystem.dto;

import kz.quizsystem.quizsystem.model.Questions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO implements Serializable {
    private String id;

    @NotBlank
    private Boolean isCorrrect;

    @NotBlank
    private String points;

    @ManyToOne
    private Questions questions;
}
