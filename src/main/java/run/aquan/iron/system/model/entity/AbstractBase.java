package run.aquan.iron.system.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.aquan.iron.system.enums.Datalevel;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * @Class AbstractBase
 * @Description TODO
 * @Author Aquan
 * @Date 2020/8/18 16:46
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class AbstractBase {

    @CreatedDate
    // @JsonIgnore
    @Column(updatable = false, name = "created_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP comment '创建时间'")
    private Instant createdTime;

    @LastModifiedDate
    // @JsonIgnore
    @Column(name = "updated_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    private Instant updatedTime;

    @CreatedBy
    // @JsonIgnore
    @Column(updatable = false, name = "created_by", columnDefinition = "varchar(255) DEFAULT NULL comment '创建者'")
    private String createdBy;

    @LastModifiedBy
    // @JsonIgnore
    @Column(name = "updated_by", columnDefinition = "varchar(255) DEFAULT NULL comment '创建者'")
    private String updatedBy;

    @Column(name = "datalevel", columnDefinition = "tinyint(1) default '1' comment '数据级别 0:已删除 1:未删除'")
    private Datalevel datalevel;

}
