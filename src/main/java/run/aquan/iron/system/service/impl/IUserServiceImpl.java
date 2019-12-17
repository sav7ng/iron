package run.aquan.iron.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.model.IUser;
import run.aquan.iron.system.service.IUserService;

/**
 * @Class IUserServiceImpl
 * @Description TODO
 * @Author Aquan
 * @Date 2019.8.13 1:07
 * @Version 1.0
 **/
@Service
@Transactional
public class IUserServiceImpl implements IUserService {
    @Override
    public boolean checkUser(String loginName, String password) {
        return true;
    }

    @Override
    public IUser getUserByLoginName(String loginName){
        IUser user = IUser.builder().id("2019").name("Aquan").age(18).build();
        return user;
    }
}