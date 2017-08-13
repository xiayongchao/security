package com.xyc.security.service;

import com.xyc.security.annotation.Module;
import com.xyc.security.annotation.Permission;

import java.util.List;
import java.util.Map;

/**
 * Created by xyc on 2017/8/10 0010.
 */
public interface SecurityService {
    void saveSecurity(Map<Module, List<Permission>> mpMap);
}
