package run.aquan.iron.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.core.AbstractService;
import run.aquan.iron.dao.UserMapper;
import run.aquan.iron.model.User;
import run.aquan.iron.service.UserService;

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
