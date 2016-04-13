package com.example.jason.fight.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Jason on 2016/4/10.
 */
public class User extends BmobUser{

    /*
    * 头像下载地址
    * */
    private String avatarurl;
    /*
    * 性别 true 为男
    * */
    private Boolean sex;
    /*
    * 昵称
    * */
    private String nickname;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }
}
