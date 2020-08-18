package run.aquan.iron.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.aquan.iron.system.model.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
