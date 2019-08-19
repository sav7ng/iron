package run.aquan.iron.service.impl;

import run.aquan.iron.dao.SysUserMapper;
import run.aquan.iron.model.SysUser;
import run.aquan.iron.service.SysUserService;
import run.aquan.iron.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
