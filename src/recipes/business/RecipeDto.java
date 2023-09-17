package recipes.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private String name;
    private String description;
    private String[] ingredients;
    private String[] directions;

    public static Recipe convertDtoToRecipe(RecipeDto dto) {
        return new Recipe(
                dto.getName(),
                dto.getDescription(),
                dto.getIngredients(),
                dto.getDirections()
        );
    }

    public static RecipeDto convertRecipeToDto(Recipe recipe) {
        return new RecipeDto(
                recipe.getName(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections()
        );
    }
}
