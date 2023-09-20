package recipes.business;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import recipes.business.entities.Recipe;
import recipes.business.entities.RecipeDto;
import recipes.persistence.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public long createRecipe(RecipeDto dto) {
        Recipe recipe = RecipeDto.convertDtoToRecipe(dto);
        recipe.setDate(LocalDateTime.now());
        repository.save(recipe);
        return recipe.getId();
    }

    public Recipe getRecipe(long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Recipe.class, "Recipe"));
    }

    public void deleteRecipe(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ObjectNotFoundException(Recipe.class, "Recipe");
        }
    }

    public void updateRecipe(long id, RecipeDto dto) {
        Recipe recipe = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Recipe.class, "Recipe"));

        RecipeDto.copyDtoFieldsToRecipe(dto, recipe);
        recipe.setDate(LocalDateTime.now());
        repository.save(recipe);
    }

    public List<Recipe> getRecipesByName(String name) {
        return repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public void createRecipes(List<RecipeDto> dtoList) {
        dtoList.forEach(this::createRecipe);
    }
}
