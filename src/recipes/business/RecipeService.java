package recipes.business;

import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import recipes.business.entities.AppUser;
import recipes.business.entities.AppUserAdapter;
import recipes.business.entities.Recipe;
import recipes.business.entities.RecipeDto;
import recipes.persistence.AppUserRepository;
import recipes.persistence.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final AppUserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, AppUserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public long createRecipe(RecipeDto dto) {
        Recipe recipe = RecipeDto.convertDtoToRecipe(dto);
        recipe.setDate(LocalDateTime.now());

        AppUser user = getUserFromRequest();
        recipe.setUser(user);

        recipeRepository.save(recipe);
        return recipe.getId();
    }

    private AppUser getUserFromRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserAdapter userDetails = (AppUserAdapter) auth.getPrincipal();
        return userRepository
                .findAppUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException(AppUser.class, "User"));
    }

    public Recipe getRecipe(long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Recipe.class, "Recipe"));
    }

    public boolean deleteRecipe(long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Recipe.class, "Recipe"));

        if (getUserFromRequest().equals(recipe.getUser())) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updateRecipe(long id, RecipeDto dto) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Recipe.class, "Recipe"));

        if (getUserFromRequest().equals(recipe.getUser())) {
            RecipeDto.copyDtoFieldsToRecipe(dto, recipe);
            recipe.setDate(LocalDateTime.now());
            recipeRepository.save(recipe);
            return true;
        }
        return false;
    }

    public List<Recipe> getRecipesByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public void createRecipes(List<RecipeDto> dtoList) {
        dtoList.forEach(this::createRecipe);
    }
}
