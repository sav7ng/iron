package run.aquan.iron.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import run.aquan.iron.system.enums.Datalevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Class SysUser
 * @Description TODO
 * @Author Aquan
 * @Date 2019.12.23 23:29
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String nickName;

    private String roles;

    private Date createTime;

    private Date updateTime;

    private Date expirationTime;

    @EnumValue
    private Datalevel datalevel;

}
