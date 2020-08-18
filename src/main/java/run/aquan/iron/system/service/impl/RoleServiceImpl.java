package run.aquan.iron.system.service.impl;

import org.springframework.stereotype.Service;
import run.aquan.iron.system.enums.RoleType;
import run.aquan.iron.system.model.entity.Role;
import run.aquan.iron.system.repository.RoleRepository;
import run.aquan.iron.system.service.RoleService;

import java.util.Optional;

/**
 * @Class RoleServiceImpl
 * @Description TODO
 * @Author Aquan
 * @Date 2020/8/18 17:32
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String init() {
        //初始化角色信息
        for (RoleType roleType : RoleType.values()) {
            Optional<Role> role = roleRepository.findByName(roleType.getName());
            if (role.isPresent()) {
                continue;
            }
            roleRepository.save(new Role(roleType.getName(), roleType.getDescription()));
        }
        return "success";
    }
}
