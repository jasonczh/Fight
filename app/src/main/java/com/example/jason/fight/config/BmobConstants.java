package com.example.jason.fight.config;

import android.os.Environment;

/**
 * Created by Jason on 2016/4/11.
 */
public class BmobConstants {

    /*
    * 存放发送图片的目录
    * */
    public static String BMOB_PICTURE_PATH= Environment.getExternalStorageDirectory()+"/examplejasonfight/image/";

    /*
    * 我的头像保存的目录
    * */
    public static String MyAvatarDir=Environment.getExternalStorageDirectory().getPath()+"/examplejasonfight/avatar/";

    /*
    * 用户名、密码，手机号相应的错误
    * */
    public final int USER_NULL = 1;
    public final int PASSWD_NULL = 2;
    public final int PASSW_CONF_NULL = 3;
    public final int MOBILE_PHONE_NUMBER_NULL = 4;
    public final int EMAIL_NULL = 5;
    public final int PASSWD_CONF_FAILED = 6;
    public final int PASSWD_LENGTH_ERROR = 7;
    public final int PHONE_NUMBER_ERROR = 8;
    public final int NICK_NAME_NULL=9;
    public final int ALL_IS_OK = 200;

}
