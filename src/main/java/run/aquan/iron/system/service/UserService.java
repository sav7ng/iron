package run.aquan.iron.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.model.dto.AuthToken;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RegisterUserParam;

public interface UserService {

    AuthToken login(LoginParam loginParam);

    AuthToken refreshToken(String refreshToken);

    User findUserByUserName(String username);

    User saveUser(RegisterUserParam registerUserParam);

    User getById(Integer id);

    Page<User> pageBy(Pageable pageable);

    String logout(JwtUser currentUser);

    String changePassword(ChangePasswordParam changePasswordParam, JwtUser currentUser);
}
