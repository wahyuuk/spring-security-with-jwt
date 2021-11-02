package co.id.wahyu.auth.jwt.service;

import co.id.wahyu.auth.jwt.model.Privilege;
import co.id.wahyu.auth.jwt.repo.PrivilegeRepo;
import co.id.wahyu.auth.jwt.service.base.BaseCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PrivilegeService implements BaseCrudService<Privilege, Long> {

    private final PrivilegeRepo privilegeRepo;

    public PrivilegeService(PrivilegeRepo privilegeRepo) {
        this.privilegeRepo = privilegeRepo;
    }

    @Override
    public List<Privilege> getAll() {
        return privilegeRepo.findAll();
    }

    @Override
    public Privilege getById(Long id) {
        return privilegeRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not found"));
    }

    @Override
    public Privilege create(Privilege data) {
        if (data.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Privilege already exist");
        }

        return privilegeRepo.save(data);
    }

    @Override
    public Privilege update(Long id, Privilege data) {
        Privilege privilege = getById(id);

        data.setId(id);

        return privilegeRepo.save(data);
    }

    @Override
    public Privilege delete(Long id) {
        Privilege privilege = getById(id);

        privilegeRepo.deleteById(id);
        return privilege;
    }
}
