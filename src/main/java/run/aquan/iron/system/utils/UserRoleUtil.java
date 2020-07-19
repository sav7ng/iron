package run.aquan.iron.system.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class UserRoleUtil
 * @Description TODO 用户角色过滤工具
 * @Author Aquan
 * @Date 2020/4/24 17:30
 * @Version 1.0
 **/
public class UserRoleUtil {

    public static String userRoleConvertToString(List<SimpleGrantedAuthority> roles) {
        List<String> result = new ArrayList<>();
        roles.stream().forEach(role -> result.add(role.toString().substring(5)));//role.toString().indexOf("_") + 1
        return StringUtils.join(result, ",");
    }

    public static List<String> userRoleConvert(List<SimpleGrantedAuthority> roles) {
        List<String> result = new ArrayList<>();
        roles.stream().forEach(role -> result.add(role.toString().substring(5)));//role.toString().indexOf("_") + 1
        return result;
    }

    public static Boolean userRoleDistinct(List<String> roles, String role) {
        long user = roles.stream().filter(s -> s.equals(role)).count();
        return user >= 1;
    }

}
