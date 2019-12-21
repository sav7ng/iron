package run.aquan.iron.system.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import run.aquan.iron.system.enums.Datalevel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", columnDefinition = "varchar(100) not null comment '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(200) not null comment '密码'")
    private String password;

    @Column(name = "nick_name", columnDefinition = "varchar(200) comment '昵称'")
    private String nickName;

    @Column(name = "roles", columnDefinition = "varchar(200) comment '权限'")
    private String roles;

    @Column(name = "sex", columnDefinition = "tinyint(1) default 0 comment '性别 0:保密 1:男 2:女'")
    private Integer sex;

    @Column(name = "create_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP comment '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "datalevel", columnDefinition = "tinyint(1) default 1 comment '数据级别 0:已删除 1:未删除'")
    private Datalevel datalevel;

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