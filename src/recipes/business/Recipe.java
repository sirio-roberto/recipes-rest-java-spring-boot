package recipes.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private static long nextId = 1L;
    private long id;
    private String name;
    private String description;
    private String[] ingredients;
    private String[] directions;

    public Recipe(String name, String description, String[] ingredients, String[] directions) {
        this.id = getNextId();
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    private static long getNextId() {
        return nextId++;
    }
}
