package run.aquan.iron.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RegisterUserParam;

public interface UserService {

    Result login(LoginParam loginParam);

    User findUserByUserName(String username);

    Result saveUser(RegisterUserParam registerUserParam);

    User getById(Integer id);

    Page<User> pageBy(Pageable pageable);

    Result logout(JwtUser currentUser);
}
