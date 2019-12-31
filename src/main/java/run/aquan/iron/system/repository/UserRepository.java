package run.aquan.iron.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndDatalevel(String username, Datalevel datalevel);

    @Transactional
    void deleteByUsername(String username);

    @Override
    Optional<User> findById(Integer id);

    Page<User> findAllByDatalevel(@NonNull Datalevel datalevel, @NonNull Pageable pageable);

}