package run.aquan.iron.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.aquan.iron.system.model.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
}
