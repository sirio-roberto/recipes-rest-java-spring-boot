package recipes.presentation;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.business.entities.RecipeDto;
import recipes.business.RecipeService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getRecipes(@RequestParam Map<String,String> allParams) {
        try {
            if (areNameAndCategoryParamsInvalid(allParams)) {
                throw new ConstraintViolationException(Set.of());
            }
            if (allParams.get("name") != null) {
                return ResponseEntity.ok(service.getRecipesByName(allParams.get("name")));
            } else {
                return ResponseEntity.ok(service.getRecipesByCategory(allParams.get("category")));
            }
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable long id) {
        try {
            if (service.deleteRecipe(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable long id, @Valid @RequestBody RecipeDto dto) {
        try {
            if (service.updateRecipe(id, dto)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Object> postRecipe(@Valid @RequestBody RecipeDto dto) {
        Map<String, Long> idObj = Map.of("id", service.createRecipe(dto));
        return ResponseEntity.ok(idObj);
    }

    @PostMapping("/newBatch")
    public ResponseEntity<Object> postRecipes(@RequestBody List<@Valid RecipeDto> dtoList) {
        service.createRecipes(dtoList);
        return ResponseEntity.ok().build();
    }

    private boolean areNameAndCategoryParamsInvalid(Map<String, String> allParams) {
        if (allParams == null || allParams.size() != 1) {
            return true;
        }
        for (String key : allParams.keySet()) {
            if (!"name".equals(key) && !"category".equals(key)) {
                return true;
            }
        }
        return false;
    }
}
