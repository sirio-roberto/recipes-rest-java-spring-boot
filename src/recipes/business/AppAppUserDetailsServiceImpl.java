package recipes.business;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.business.entities.AppUser;
import recipes.business.entities.AppUserAdapter;
import recipes.persistence.AppUserRepository;

@Service
public class AppAppUserDetailsServiceImpl implements AppUserDetailsService {
    private final AppUserRepository repository;

    public AppAppUserDetailsServiceImpl(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository
                .findAppUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new AppUserAdapter(user);
    }
}
