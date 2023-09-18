package recipes.business;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    private final List<Recipe> recipes = new ArrayList<>();

    public long createRecipe(RecipeDto dto) {
        Recipe recipe = RecipeDto.convertDtoToRecipe(dto);
        recipes.add(recipe);
        return recipe.getId();
    }

    public RecipeDto getRecipe(long id) {
        return recipes.stream()
                .filter(recipe -> recipe.getId() == id)
                .map(RecipeDto::convertRecipeToDto)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Id not found"));
    }

    public void deleteRecipe(long id) {
        if (!recipes.removeIf(recipe -> recipe.getId() == id)) {
            throw new IllegalArgumentException("Id not found");
        }
    }
}
