package co.id.wahyu.auth.jwt.repo;

import co.id.wahyu.auth.jwt.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {
}
