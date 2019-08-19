package run.aquan.iron.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;
import javax.persistence.*;

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

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取UUID
     *
     * @return sys_user_id - UUID
     */
    public String getSysUserId() {
        return sysUserId;
    }

    /**
     * 设置UUID
     *
     * @param sysUserId UUID
     */
    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * 获取用户名
     *
     * @return sys_user_name - 用户名
     */
    public String getSysUserName() {
        return sysUserName;
    }

    /**
     * 设置用户名
     *
     * @param sysUserName 用户名
     */
    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    /**
     * 获取密码

     *
     * @return sys_user_password - 密码

     */
    public String getSysUserPassword() {
        return sysUserPassword;
    }

    /**
     * 设置密码

     *
     * @param sysUserPassword 密码

     */
    public void setSysUserPassword(String sysUserPassword) {
        this.sysUserPassword = sysUserPassword;
    }

    /**
     * 获取邮箱
     *
     * @return sys_user_email - 邮箱
     */
    public String getSysUserEmail() {
        return sysUserEmail;
    }

    /**
     * 设置邮箱
     *
     * @param sysUserEmail 邮箱
     */
    public void setSysUserEmail(String sysUserEmail) {
        this.sysUserEmail = sysUserEmail;
    }

    /**
     * 获取手机号码
     *
     * @return sys_user_phone - 手机号码
     */
    public String getSysUserPhone() {
        return sysUserPhone;
    }

    /**
     * 设置手机号码
     *
     * @param sysUserPhone 手机号码
     */
    public void setSysUserPhone(String sysUserPhone) {
        this.sysUserPhone = sysUserPhone;
    }

    /**
     * 获取权限角色
     *
     * @return sys_role_id - 权限角色
     */
    public Integer getSysRoleId() {
        return sysRoleId;
    }

    /**
     * 设置权限角色
     *
     * @param sysRoleId 权限角色
     */
    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    /**
     * 获取创建时间

     *
     * @return sys_create_time - 创建时间

     */
    public Date getSysCreateTime() {
        return sysCreateTime;
    }

    /**
     * 设置创建时间

     *
     * @param sysCreateTime 创建时间

     */
    public void setSysCreateTime(Date sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    /**
     * 获取昵称
     *
     * @return sys_nick_name - 昵称
     */
    public String getSysNickName() {
        return sysNickName;
    }

    /**
     * 设置昵称
     *
     * @param sysNickName 昵称
     */
    public void setSysNickName(String sysNickName) {
        this.sysNickName = sysNickName;
    }
}