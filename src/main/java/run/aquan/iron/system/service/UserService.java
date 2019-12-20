package run.aquan.iron.system.service;

import org.springframework.data.domain.Page;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.RegisterUserParam;

import java.util.Optional;

public interface UserService {

    User findUserByUserName(String userName);

    void saveUser(RegisterUserParam registerUserParam);

    Page<User> pageBy(Integer pageNum, Integer pageSize);

    User getById(Integer id);

}
