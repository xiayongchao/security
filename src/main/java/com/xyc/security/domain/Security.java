package com.xyc.security.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限实体（权限模块+权限）
 * Created by xyc on 2017/8/9 0009.
 */
@Entity
@Table(name = "T_PERMISSION", catalog = "test")
public class Security implements Serializable {
    private static final long serialVersionUID = -6294525733922731263L;
    /**
     * 权限主键
     */
    @Id
    @GeneratedValue
    @Column(name = "SID")
    private Long sid;
    /**
     * 权限的模块ID
     */
    @Column(name = "PID")
    private Long pid;
    /**
     * 权限名称
     */
    @Column(name = "NAME", nullable = false)
    private String name;
    /**
     * 权限值
     */
    @Column(name = "VALUE", nullable = false)
    private String value;

    public Security() {
    }

    public Security(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Security(Long pid, String name, String value) {
        this.pid = pid;
        this.name = name;
        this.value = value;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
