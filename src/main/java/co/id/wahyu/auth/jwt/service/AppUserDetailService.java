package co.id.wahyu.auth.jwt.service;

import co.id.wahyu.auth.jwt.model.AppUserDetail;
import co.id.wahyu.auth.jwt.model.User;
import co.id.wahyu.auth.jwt.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public AppUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not registered"));

        return new AppUserDetail(user);
    }
}
