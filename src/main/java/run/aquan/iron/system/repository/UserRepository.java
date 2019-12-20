package run.aquan.iron.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    @Override
    Optional<User> findById(Integer id);

}