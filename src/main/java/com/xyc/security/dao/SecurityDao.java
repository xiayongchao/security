package com.xyc.security.dao;

import com.xyc.security.domain.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 权限的数据访问层，使用spring jpa
 * Created by xyc on 2017/8/8 0008.
 */
@Repository
public interface SecurityDao extends JpaRepository<Security, Long> {
}
