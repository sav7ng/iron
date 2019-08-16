package run.aquan.iron.service;

import run.aquan.iron.model.IUser;

public interface IUserService {
    boolean checkUser(String loginName, String password);

    IUser getUserByLoginName(String loginName);
}
