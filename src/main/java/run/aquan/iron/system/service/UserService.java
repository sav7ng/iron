package run.aquan.iron.system.service;

import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.security.token.AuthToken;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RegisterUserParam;

import java.util.List;

public interface UserService {

    AuthToken login(LoginParam loginParam);

    User findUserByUserName(String username);

    Integer saveUser(RegisterUserParam registerUserParam);

    User getById(Integer id);

    String logout(JwtUser currentUser);

    String changePassword(ChangePasswordParam changePasswordParam, JwtUser currentUser);

    List<User> mybaisPlusGetUser();

    AuthToken refreshToken(String refreshToken);
}
