package com.ecard.entity;

import java.util.Date;

public class Authority {
    private Long authorityNo;

    private String authorityName;

    private String img;

    private String url;

    private String evntName;

    private String createUser;

    private Date creatTime;

    private String updateUser;

    private Date updateTime;

    private Integer version;

    public Long getAuthorityNo() {
        return authorityNo;
    }

    public void setAuthorityNo(Long authorityNo) {
        this.authorityNo = authorityNo;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName == null ? null : authorityName.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getEvntName() {
        return evntName;
    }

    public void setEvntName(String evntName) {
        this.evntName = evntName == null ? null : evntName.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}