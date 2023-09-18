package recipes.business;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import recipes.persistence.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public long createRecipe(RecipeDto dto) {
        Recipe recipe = RecipeDto.convertDtoToRecipe(dto);
        repository.save(recipe);
        return recipe.getId();
    }

    public RecipeDto getRecipe(long id) {
        Optional<Recipe> recipe = repository.findById(id);
        if (recipe.isPresent()) {
            return RecipeDto.convertRecipeToDto(recipe.get());
        }
        throw new ObjectNotFoundException(Recipe.class, "Recipe");
    }

    public void deleteRecipe(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ObjectNotFoundException(Recipe.class, "Recipe");
        }
    }
}
