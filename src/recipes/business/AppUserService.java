package recipes.business;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.business.entities.AppUser;
import recipes.persistence.AppUserRepository;

import javax.validation.ConstraintViolationException;
import java.util.Set;

@Service
public class AppUserService {
    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void registerUser(AppUser user) {
//        if (repository.findAppUserByEmail(user.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email is already taken!");
//        }
        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);
    }

    public Iterable<AppUser> getAllUsers() {
        return repository.findAll();
    }
}
