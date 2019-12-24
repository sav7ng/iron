package run.aquan.iron.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.model.entity.User;

import java.util.Collection;

/**
 * @Class JwtUser
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/19 16:35
 * @Version 1.0
 **/
public class JwtUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {

    }

    /**
     * 通过 user 对象创建jwtUser
     */
    public JwtUser(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        authorities = user.getRoles();
    }

    /**
     * 通过 SysUser 对象创建jwtUser
     */
    public JwtUser(SysUser sysUser) {
        id = sysUser.getId();
        username = sysUser.getUsername();
        password = sysUser.getPassword();
        authorities = sysUser.getRoles();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}

