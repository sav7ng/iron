package run.aquan.iron.system.service;

import run.aquan.iron.system.model.User;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * Created by CodeGenerator on 2019/08/10.
 */
public interface UserService {

    Optional<User> getOptional(@NotBlank Integer id);

    User getById(@NotBlank Integer id);

}
