package com.app.xxnr.bean.dbbeans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class DatasBean {
    @Id
    public String _id;
    public String account;
    public String password;
    /**
     * _id : 56556231f5dc2482e8831ae9
     * name : customer_op
     * view_roles : ["dashboard","orders","users","rewardshop"]
     */
    @Transient
    public RoleBean role;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Generated(hash = 482504291)
    public DatasBean(String _id, String account, String password) {
        this._id = _id;
        this.account = account;
        this.password = password;
    }

    @Generated(hash = 128729784)
    public DatasBean() {
    }

    public  class RoleBean {
        public String _id;
        public List<String> view_roles;
    }


}