package run.aquan.iron.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.core.AbstractService;
import run.aquan.iron.system.dao.UserMapper;
import run.aquan.iron.system.model.User;
import run.aquan.iron.system.service.UserService;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/08/10.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
