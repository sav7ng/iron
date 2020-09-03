package run.aquan.iron.system.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Class User
 * @Description TODO
 * @Author Aquan
 * @Date 2019.12.23 23:29
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "User")
@Table(name = "user")
public class User extends AbstractBase {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(36) NOT NULL comment 'USER_ID'")
    private String id;

    @Column(name = "username", columnDefinition = "varchar(100) not null comment '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(200) not null comment '密码'")
    private String password;

    @Column(name = "nick_name", columnDefinition = "varchar(200) comment '昵称'")
    private String nickName;

    @Column(name = "sex", columnDefinition = "tinyint(1) default '0' comment '性别 0:保密 1:男 2:女'")
    private Integer sex;

    @Column(name = "enabled", columnDefinition = "tinyint(1) default 1 comment '是否启用 0:禁用 1:启用'")
    private Boolean enabled;

    @Column(name = "expiration_time", columnDefinition = "timestamp comment 'token过期时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> userRoles = new ArrayList<>();

    public List<SimpleGrantedAuthority> getRoles() {
        /**
         * TODO: 只取索引第 1 到第 2 位的：
         *  int[] a = {1, 2, 3, 4};
         *  Arrays.stream(a, 1, 3).forEach(System.out :: println);
         *  打印 2 ，3
         **/
        List<Role> roles = userRoles.stream().map(UserRole::getRole).collect(Collectors.toList());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

}