package co.id.wahyu.auth.jwt.repo;

import co.id.wahyu.auth.jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    List<Role> findByUsers_id(Long userId);
    Optional<Role> findByIdAndUsers_id(Long roleId, Long userId);
}
