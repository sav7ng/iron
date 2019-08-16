package run.aquan.iron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.enums.ApiResponseEnum;
import run.aquan.iron.model.ApiResponse;
import run.aquan.iron.model.IUser;
import run.aquan.iron.service.IUserService;
import run.aquan.iron.utils.ApiResponseUtil;
import run.aquan.iron.utils.JwtUtil;

import java.util.Map;

/**
 * @Class LoginController
 * @Description TODO
 * @Author Aquan
 * @Date 2019.8.13 1:02
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class LoginController {


    @Autowired
    private IUserService iUserService;

    /**
     * 登陆接口
     *
     * @return token
     */
    @PostMapping(value = "/login")
    public ApiResponse login(@RequestBody Map<String, String> map) {

        String loginName = map.get("loginName");
        String password = map.get("password");
        //身份验证是否成功
        boolean isSuccess = iUserService.checkUser(loginName, password);
        if (isSuccess) {
            IUser user = iUserService.getUserByLoginName(loginName);
            if (user != null) {
                //返回token
                String token = JwtUtil.sign(user.getName(), user.getId());
                if (token != null) {
                    return ApiResponseUtil.getApiResponse(token);
                }
            }
        }
        //返回登陆失败消息
        return ApiResponseUtil.getApiResponse(ApiResponseEnum.LOGIN_FAIL);
    }

}