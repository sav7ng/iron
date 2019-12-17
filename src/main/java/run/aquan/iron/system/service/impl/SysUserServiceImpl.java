package run.aquan.iron.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.core.AbstractService;
import run.aquan.iron.system.dao.SysUserMapper;
import run.aquan.iron.system.model.SysUser;
import run.aquan.iron.system.service.SysUserService;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/08/17.
 */
@Service
@Transactional
public class SysUserServiceImpl extends AbstractService<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

}
