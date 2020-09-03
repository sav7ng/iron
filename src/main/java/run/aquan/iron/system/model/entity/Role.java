package run.aquan.iron.system.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.aquan.iron.system.model.entity.support.AbstractBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class Role
 * @Description TODO
 * @Author Aquan
 * @Date 2020/8/18 16:42
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "Role")
@Table(name = "role")
public class Role extends AbstractBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) NOT NULL comment 'ROLE_ID'")
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(255) DEFAULT NULL comment '身份CODE'")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(255) DEFAULT NULL comment '身份描述'")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> userRoles = new ArrayList<>();

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
