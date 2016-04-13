package com.example.jason.fight.utlis;

import android.content.Context;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Jason on 2016/4/12.
 */
public class BmobFileUtil {

    //public static final String UPDATE_ERROR;

    /*
    * 上传文件，单个文件不能大于200M
    * */
    public static String updateFile(File file, final Context mContext){
        final BmobFile bmobFile=new BmobFile(file);
        final String[] url = new String[1];
        bmobFile.uploadblock(mContext, new UploadFileListener() {
            @Override
            public void onSuccess() {
                url[0] =bmobFile.getFileUrl(mContext);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
        return url[0];
    }
}
