package com.wizlah.es.entity;

import com.wizlah.es.commons.SnowFlake;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {
  private static final long serialVersionUID = -3098146206519655940L;

  @Id
  @Column(nullable = false, unique = true)
  private long id = SnowFlake.getInstance().generateLongId();

  @Column(nullable = false)
  @CreatedDate
  protected long createTime = System.currentTimeMillis();

  @Column(nullable = false)
  @LastModifiedDate
  protected long updateTime = System.currentTimeMillis();

  @CreatedBy
  @Column(nullable = false)
  protected long createBy;

  @Column(nullable = false)
  @LastModifiedBy
  protected long updateBy;
}
