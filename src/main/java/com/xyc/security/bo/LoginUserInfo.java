package com.xyc.security.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 登录用户信息
 * Created by xyc on 2017/8/13 0013.
 */
public class LoginUserInfo implements Serializable {
    private static final long serialVersionUID = 4925564717462126047L;
    /**
     * 登录用户的权限
     */
    private Map<String, List<String>> mpInfoMap;

    public LoginUserInfo(Map<String, List<String>> mpInfoMap) {
        this.mpInfoMap = mpInfoMap;
    }

    public Map<String, List<String>> getMpInfoMap() {
        return mpInfoMap;
    }

    public void setMpInfoMap(Map<String, List<String>> mpInfoMap) {
        this.mpInfoMap = mpInfoMap;
    }
}
