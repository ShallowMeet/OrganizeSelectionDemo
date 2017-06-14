package com.example.mydemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.mydemo.adapter.OrgContactAdapter;

import java.util.ArrayList;


public class OrgVo implements MultiItemEntity, java.io.Serializable {
//ZF机构
    private static final long serialVersionUID = 1L;
    private String id;//机构ID
    private String code;//机构编码
    private String name;//机构名称
    private String pid;//上级

    private String count;//部门人数
    private ArrayList<EmpUserVo> users;//机构所属用户
    private ArrayList<OrgVo> orgVOs;//下属机构

    private String incomeData;//入职时间
    private String telephone;//固话

    public String getIncomeData() {
        return incomeData;
    }

    public void setIncomeData(String incomeData) {
        this.incomeData = incomeData;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public ArrayList<EmpUserVo> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<EmpUserVo> users) {
        this.users = users;
    }

    public ArrayList<OrgVo> getOrgVOs() {
        return orgVOs;
    }

    public void setOrgVOs(ArrayList<OrgVo> orgVOs) {
        this.orgVOs = orgVOs;
    }

    @Override
    public int getItemType() {
        return OrgContactAdapter.ORG;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrgVo) {
            OrgVo vo = (OrgVo) obj;
            return (id.equals(vo.id));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();

    }
}
