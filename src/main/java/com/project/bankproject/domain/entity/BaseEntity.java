package com.project.bankproject.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * abstract class BaseEntity - super-class for others Entities.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter(PRIVATE)
@MappedSuperclass
public abstract class BaseEntity {
    /**
     * UUID id of all Entities
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
