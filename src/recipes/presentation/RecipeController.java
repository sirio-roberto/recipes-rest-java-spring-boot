package recipes.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.business.RecipeDto;
import recipes.business.RecipeService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRecipe(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.getRecipe(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable long id) {
        try {
            service.deleteRecipe(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Object> postRecipe(@Valid @RequestBody RecipeDto dto) {
        Map<String, Long> idObj = Map.of("id", service.createRecipe(dto));
        return ResponseEntity.ok(idObj);
    }
}
