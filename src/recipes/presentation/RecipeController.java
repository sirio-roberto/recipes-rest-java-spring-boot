package recipes.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.business.RecipeDto;
import recipes.business.RecipeService;

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

    @PostMapping("/new")
    public ResponseEntity<Object> postRecipe(@RequestBody RecipeDto dto) {
        Map<String, Long> idObj = Map.of("id", service.createRecipe(dto));
        return ResponseEntity.ok(idObj);
    }
}
