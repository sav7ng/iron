package run.aquan.iron.security.entity;

import lombok.Data;

/**
 * @Class LoginUser
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/19 16:38
 * @Version 1.0
 **/
@Data
public class LoginUser {

    private String userName;
    private String password;
    private Boolean rememberMe;

}