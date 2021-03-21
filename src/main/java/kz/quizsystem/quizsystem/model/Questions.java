package kz.quizsystem.quizsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("question")
@TypeAlias("Question")
public class Questions implements Serializable {
    @JsonProperty("id")
    @MongoId(targetType = FieldType.OBJECT_ID)
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
