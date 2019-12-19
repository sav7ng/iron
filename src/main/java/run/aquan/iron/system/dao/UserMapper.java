package run.aquan.iron.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import run.aquan.iron.system.model.User;

import java.util.Optional;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // @Select("SELECT * FROM user WHERE username = ")
    Optional<User> findUserByUserName(@Param(value = "userName") String userName);

}