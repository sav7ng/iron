package run.aquan.iron.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.aquan.iron.system.model.entity.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Integer> {


}
