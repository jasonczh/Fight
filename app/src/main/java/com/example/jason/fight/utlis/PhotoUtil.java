package com.example.jason.fight.utlis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jason on 2016/4/11.
 */
public class PhotoUtil {
    /*
    * 回收垃圾recycle
    * */
    public static void recycle(Bitmap bitmap){
        // 先判断是否已经回收
        if (bitmap != null && !bitmap.isRecycled()) {
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }

    /*
    * 获取指定路劲下的图片的指定大小的缩略图
    *
    * */

    public static Bitmap getImageWithSize(String imagePath,int width,int height){
        Bitmap bitmap=null;
        BitmapFactory.Options options=new BitmapFactory.Options();
        //在不获取位图数据源的情况下，得到图片的长和宽
        options.inJustDecodeBounds=true;
        bitmap=BitmapFactory.decodeFile(imagePath, options);
        //通过一定算法计算imSampleSize的合适值
        options.inSampleSize=calculateInSampleSize(options,200,200);
        options.inJustDecodeBounds=false;
        //重新载入图片，读取缩放后的bitmap，记得设置inJustDecodeBounds  为false
        bitmap=BitmapFactory.decodeFile(imagePath,options);
        //可以利用ThumbnailUtils来创建缩略图，这里要指定要缩放的哪个bitmap对象
        //bitmap= ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    private  static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        int width=options.outWidth;
        int height=options.outHeight;
        int inSampleSize=1;
        if(width>reqWidth || height>reqHeight){
            int widthRadio=Math.round(width*1.0f/reqWidth);
            int heightRadio=Math.round(height*1.0f/reqHeight);
        }
        return inSampleSize;
    }

    /*
    * 保存Bitmap到指定的路径中
    * @param isDelete 是否只留一张，多余删除
    * */
    public static void saveBitmap(String dirpath,String filename,Bitmap bitmap,boolean isDelete){
        File dir=new File(dirpath);
        if(!dir.exists()){
            dir.mkdirs();//mkdirs:如果父级文件不再在一起创建 mkdir:父级文件存在
        }
        File file=new File(dirpath,filename);
        //若存在即删除--默认只存在一张
        if(isDelete){
            if(file.exists()){
                file.delete();
            }
        }
        if(!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream out=null;
        try{
            out=new FileOutputStream(file);
            //将图片压缩后存到out中
            if(bitmap.compress(Bitmap.CompressFormat.PNG,100,out)){
                out.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
