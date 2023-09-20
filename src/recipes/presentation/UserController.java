package recipes.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.business.entities.AppUser;
import recipes.business.AppUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final AppUserService service;

    public UserController(AppUserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody AppUser user) {
        service.registerUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allUsers")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }
}
