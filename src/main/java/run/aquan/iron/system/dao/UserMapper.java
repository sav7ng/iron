package run.aquan.iron.system.dao;

import org.apache.ibatis.annotations.Mapper;
import run.aquan.iron.system.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}