/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */
package edu.ucsb.xuanwang.recyclerviewdragreorder;

/**
 * Created in 2016/5/7 18:33.
 *
 * @author Yolanda;
 */
public class UserInfo {

    /**
     * 名字
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 是否选中
     */
    private boolean isCheck;

    public UserInfo() {
    }

    public UserInfo(String name, String sex, boolean isCheck) {
        this.name = name;
        this.sex = sex;
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
