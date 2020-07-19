package run.aquan.iron.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.model.entity.SysUser;

import java.util.Optional;

/**
 * @Class SysUserMapper
 * @Description TODO
 * @Author Aquan
 * @Date 2020.7.19 12:48
 * @Version 1.0
 **/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where username = #{username} and datalevel = #{datalevel}")
    Optional<SysUser> findByUsernameAndDatalevel(@Param("username")String username, @Param("datalevel")Datalevel datalevel);

}
