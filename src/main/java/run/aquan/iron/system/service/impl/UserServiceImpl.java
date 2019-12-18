package run.aquan.iron.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.dao.UserMapper;
import run.aquan.iron.system.model.User;
import run.aquan.iron.system.service.UserService;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Optional;


/**
 * Created by CodeGenerator on 2019/08/10.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Optional<User> getOptional(Integer id) {
        User user = userMapper.selectById(id);
        return Optional.ofNullable(user);
    }

    @Override
    public User getById(@NotBlank Integer id) {
        return userMapper.selectById(id);
    }


}
