package run.aquan.iron.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.model.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        authorities = userRoleConvert(user.getRoles());
    }

    /**
     * 通过 SysUser 对象创建jwtUser
     */
    public JwtUser(SysUser sysUser) {
        id = sysUser.getId();
        username = sysUser.getUsername();
        password = sysUser.getPassword();
        authorities = userRoleConvert(sysUser.getRoles());
    }

    public Integer getId() {
        return id;
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

    public List<SimpleGrantedAuthority> userRoleConvert(String roles) {
        /**
         * TODO: 只取索引第 1 到第 2 位的：
         *  int[] a = {1, 2, 3, 4};
         *  Arrays.stream(a, 1, 3).forEach(System.out :: println);
         *  打印 2 ，3
         **/
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return authorities;
    }

}

