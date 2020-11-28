package run.aquan.iron.system.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import run.aquan.iron.system.model.entity.support.AbstractEntityBase;

import javax.persistence.*;
import java.util.Date;

/**
 * @Class SysUser
 * @Description
 * @Author Saving
 * @Date 2019.12.23 23:29
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "sys_user")
public class SysUser extends AbstractEntityBase {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(36) NOT NULL comment 'ID'")
    private String id;

    @Column(name = "username", columnDefinition = "varchar(100) not null comment '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(200) not null comment '密码'")
    private String password;

    @Column(name = "nick_name", columnDefinition = "varchar(200) comment '昵称'")
    private String nickName;

    @Column(name = "roles", columnDefinition = "varchar(200) comment '权限'")
    private String roles;

    @Column(name = "expiration_time", columnDefinition = "timestamp comment 'token过期时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;

}
