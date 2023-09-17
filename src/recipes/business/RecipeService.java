package recipes.business;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private List<Recipe> recipes = new ArrayList<>();

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
}
