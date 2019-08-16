package run.aquan.iron.core;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @Class Mapper
 * @Description 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 * @Author Aquan
 * @Date 2019/8/16 14:51
 * @Version 1.0
 **/
public interface Mapper<T>
        extends
        BaseMapper<T>,
        ConditionMapper<T>,
        IdsMapper<T>,
        InsertListMapper<T> {
}
