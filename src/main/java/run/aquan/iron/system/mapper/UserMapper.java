package run.aquan.iron.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.model.entity.User;

import java.util.Optional;

/**
 * @Class UserMapper
 * @Description TODO
 * @Author Aquan
 * @Date 2020.7.19 11:15
 * @Version 1.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username} and datalevel = #{datalevel}")
    Optional<User> findByUsernameAndDatalevel(@Param("username")String username, @Param("datalevel")Datalevel datalevel);

}
