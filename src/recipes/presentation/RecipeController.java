package recipes.presentation;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import recipes.business.AppUser;
import recipes.business.RecipeDto;
import recipes.business.RecipeService;
import recipes.persistence.AppUserRepository;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RecipeController {
    private final RecipeService service;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RecipeController(RecipeService service, AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Object> getRecipe(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.getRecipe(id));
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recipe/search")
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

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable long id) {
        try {
            service.deleteRecipe(id);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable long id, @Valid @RequestBody RecipeDto dto) {
        try {
            service.updateRecipe(id, dto);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Object> postRecipe(@Valid @RequestBody RecipeDto dto) {
        Map<String, Long> idObj = Map.of("id", service.createRecipe(dto));
        return ResponseEntity.ok(idObj);
    }

    @PostMapping("/recipe/newBatch")
    public ResponseEntity<Object> postRecipes(@RequestBody List<@Valid RecipeDto> dtoList) {
        service.createRecipes(dtoList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        userRepository.save(appUser);
        return ResponseEntity.ok("New user successfully registered");
    }

    @GetMapping("/allUsers")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
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
