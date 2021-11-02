package co.id.wahyu.auth.jwt.service;

import co.id.wahyu.auth.jwt.model.Role;
import co.id.wahyu.auth.jwt.repo.RoleRepo;
import co.id.wahyu.auth.jwt.service.base.BaseCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoleService implements BaseCrudService<Role, Long> {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public List<Role> getAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    }

    @Override
    public Role create(Role data) {
        if (data.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exist");
        }

        return roleRepo.save(data);
    }

    @Override
    public Role update(Long id, Role data) {
        Role role = getById(id);

        data.setId(id);

        return roleRepo.save(data);
    }

    @Override
    public Role delete(Long id) {
        Role role = getById(id);

        roleRepo.deleteById(id);
        return role;
    }
}
