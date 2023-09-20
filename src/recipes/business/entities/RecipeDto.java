package recipes.business.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private String name;
    private String category;
    private String description;
    private List<String> ingredients;
    private List<String> directions;

    public static Recipe convertDtoToRecipe(RecipeDto dto) {
        return new Recipe(
                dto.getName(),
                dto.getCategory(),
                dto.getDescription(),
                dto.getIngredients(),
                dto.getDirections()
        );
    }

    public static RecipeDto convertRecipeToDto(Recipe recipe) {
        return new RecipeDto(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections()
        );
    }

    public static void copyDtoFieldsToRecipe(RecipeDto dto, Recipe recipe) {
        recipe.setName(dto.getName());
        recipe.setCategory(dto.getCategory());
        recipe.setDescription(dto.getDescription());
        recipe.setIngredients(dto.getIngredients());
        recipe.setDirections(dto.getDirections());
    }
}
