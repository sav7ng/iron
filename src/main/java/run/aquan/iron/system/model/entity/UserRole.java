package run.aquan.iron.system.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.aquan.iron.system.model.entity.support.AbstractBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Class UserRole
 * @Description TODO
 * @Author Aquan
 * @Date 2020/8/18 16:40
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=false)
@Entity(name = "UserRole")
@Table(name = "user_role")
public class UserRole extends AbstractBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) NOT NULL comment 'ID'")
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

}
