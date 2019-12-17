package run.aquan.iron.system.service;

import run.aquan.iron.system.model.IUser;

public interface IUserService {
    boolean checkUser(String loginName, String password);

    IUser getUserByLoginName(String loginName);
}
