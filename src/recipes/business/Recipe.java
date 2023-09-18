package recipes.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Size(min = 1)
    private String[] ingredients;

    @NotNull
    @Size(min = 1)
    private String[] directions;

    public Recipe(String name, String description, @Size(min = 1) String[] ingredients, @Size(min = 1) String[] directions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}
