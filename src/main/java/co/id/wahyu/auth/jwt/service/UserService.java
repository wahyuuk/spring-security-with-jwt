package co.id.wahyu.auth.jwt.service;

import co.id.wahyu.auth.jwt.model.User;
import co.id.wahyu.auth.jwt.repo.UserRepo;
import co.id.wahyu.auth.jwt.service.base.BaseCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService implements BaseCrudService<User, Long> {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public User create(User data) {
        if (data.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }

        return userRepo.save(data);
    }

    @Override
    public User update(Long id, User data) {
        User user = getById(id);

        data.setId(id);

        return userRepo.save(data);
    }

    @Override
    public User delete(Long id) {
        User user = getById(id);
        userRepo.deleteById(id);

        return user;
    }
}
