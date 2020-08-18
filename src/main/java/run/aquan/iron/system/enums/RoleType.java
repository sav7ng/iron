package run.aquan.iron.system.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    USER("USER", "用户"),
    TEMP_USER("TEMP_USER", "临时用户"),
    MANAGER("MANAGER", "管理者"),
    ADMIN("ADMIN", "Admin");
    java.lang.String name;
    java.lang.String description;

    RoleType(java.lang.String name, java.lang.String description) {
        this.name = name;
        this.description = description;
    }

}
