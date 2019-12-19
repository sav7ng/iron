package run.aquan.iron.system.service;

import run.aquan.iron.system.model.User;
import run.aquan.iron.system.model.params.RegisterUserParam;

import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> getOptional(@NotBlank Integer id);

    User getById(@NotBlank Integer id);

    User findUserByUserName(String userName);

    void saveUser(RegisterUserParam registerUserParam);

}
