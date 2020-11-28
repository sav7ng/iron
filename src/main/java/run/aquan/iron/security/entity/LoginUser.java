package run.aquan.iron.security.entity;

import lombok.Data;

/**
 * @Class LoginUser
 * @Description
 * @Author Saving
 * @Date 2019/12/19 16:38
 * @Version 1.0
 **/
@Data
public class LoginUser {

    private String username;
    private String password;
    private Boolean rememberMe;

}
