package io.github.ddongeee.search.domain;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class MutableBaseEntity extends BaseEntity {
    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(updatable = true)
    private String modifiedBy;
}
