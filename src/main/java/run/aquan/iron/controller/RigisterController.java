package run.aquan.iron.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.core.Result;
import run.aquan.iron.core.ResultGenerator;
import run.aquan.iron.model.SysUser;
import run.aquan.iron.service.SysUserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* Created by CodeGenerator on 2019/08/17.
*/
@RestController
@RequestMapping("sys/user")
public class RigisterController {
    @Resource
    private SysUserService sysUserService;

    @PostMapping("add")
    public Result add(SysUser sysUser) {

        if (sysUser != null){

            sysUser.setSysUserId(IdUtil.simpleUUID());//hutool-UUiD无 "-"
            sysUser.setSysUserPassword(SecureUtil.md5(sysUser.getSysUserPassword()));
            sysUser.setSysNickName(StringUtils.isNotEmpty(sysUser.getSysNickName())?sysUser.getSysNickName():sysUser.getSysUserName());
            sysUser.setSysCreateTime(new Date());

            sysUserService.save(sysUser);

            return ResultGenerator.genSuccessResult(sysUser);

        }

        return ResultGenerator.genFailResult("注册失败！");


    }

    @PostMapping("delete")
    public Result delete(@RequestParam Integer id) {
        sysUserService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(SysUser sysUser) {
        sysUserService.update(sysUser);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("detail")
    public Result detail(@RequestParam Integer id) {
        SysUser sysUser = sysUserService.findById(id);
        return ResultGenerator.genSuccessResult(sysUser);
    }

    @PostMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<SysUser> list = sysUserService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
