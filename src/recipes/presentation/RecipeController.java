package recipes.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.business.Recipe;

@RestController
@RequestMapping("/api")
public class RecipeController {
    private Recipe recipe = new Recipe();

    @GetMapping("recipe")
    public ResponseEntity<Object> getRecipe() {
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("recipe")
    public ResponseEntity<Object> postRecipe(@RequestBody Recipe recipe) {
        this.recipe = recipe;
        return ResponseEntity.ok(recipe);
    }
}
