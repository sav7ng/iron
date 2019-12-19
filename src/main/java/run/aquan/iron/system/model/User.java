package run.aquan.iron.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String nickName;

    private String roles;

    private Integer sex;

    private Date registerDate;

    public List<SimpleGrantedAuthority> getRoles() {
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