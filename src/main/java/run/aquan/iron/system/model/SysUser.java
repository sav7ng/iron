package run.aquan.iron.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@Table(name = "sys_user")
public class SysUser {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * UUID
     */
    @Column(name = "sys_user_id")
    private String sysUserId;

    /**
     * 用户名
     */
    @Column(name = "sys_user_name")
    private String sysUserName;

    /**
     * 密码

     */
    @Column(name = "sys_user_password")
    private String sysUserPassword;

    /**
     * 邮箱
     */
    @Column(name = "sys_user_email")
    private String sysUserEmail;

    /**
     * 手机号码
     */
    @Column(name = "sys_user_phone")
    private String sysUserPhone;

    /**
     * 权限角色
     */
    @Column(name = "sys_role_id")
    private Integer sysRoleId;

    /**
     * 创建时间

     */
    @Column(name = "sys_create_time")
    private Date sysCreateTime;

    /**
     * 昵称
     */
    @Column(name = "sys_nick_name")
    private String sysNickName;

}