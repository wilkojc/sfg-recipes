package guru.springframework.recipes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "recipe")
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private Recipe recipe;

    @Lob
    private String content;

    public Notes(){}

    public Notes (String content) {
        this.content = content;
    }
}
