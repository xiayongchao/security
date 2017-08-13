package com.xyc.security.service.impl;

import com.xyc.security.annotation.Module;
import com.xyc.security.annotation.Permission;
import com.xyc.security.dao.SecurityDao;
import com.xyc.security.domain.Security;
import com.xyc.security.service.SecurityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by xyc on 2017/8/10 0010.
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    @Resource
    private SecurityDao securityDao;

    @Override
    public void saveSecurity(Map<Module, List<Permission>> mpMap) {
        if (mpMap == null || mpMap.isEmpty()) {
            return;
        }
        this.securityDao.deleteAll();
        mpMap.forEach((m, pList) -> {
            Security permission = new Security(m.name(), m.value());
            this.securityDao.save(permission);
            pList.forEach(p -> {
                this.securityDao.save(new Security(permission.getSid(), p.name(), p.value()));
            });
        });
    }
}
