package com.example.jason.fight.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.fight.R;
import com.example.jason.fight.bean.User;
import com.example.jason.fight.config.BmobConstants;
import com.example.jason.fight.utlis.PhotoUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jason on 2016/4/10.
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener {

    /*
    * 相册/拍照/剪切对应requestcode
    * */
    private final int PHOTO_REQUEST_GALLERY = 1000;
    private final int PHOTO_REQUEST_TAKEPHOTO = 2000;
    private final int PHOTO_REQUEST_CUT = 3000;

    private final int USER_NULL = 1;
    private final int PASSWD_NULL = 2;
    private final int PASSW_CONF_NULL = 3;
    private final int MOBILE_PHONE_NUMBER_NULL = 4;
    private final int EMAIL_NULL = 5;
    private final int PASSWD_CONF_FAILED = 6;
    private final int PASSWD_LENGTH_ERROR = 7;
    private final int PHONE_NUMBER_ERROR = 8;
    private final int NICK_NAME_NULL=9;
    private final int ALL_IS_OK = 200;

    private String user;
    private String passwd;
    private String passwdConf;
    private String phone;
    private String email;
    private String nick;

    private final String appId = "10565e43fd0e9dac47e31d501a10e3f0";

    private final String filename=getPhotoFileNmae();
    private File file=new File(BmobConstants.MyAvatarDir,filename);

    private CircleImageView cImg;
    private TextView updateTv;
    private EditText userEdit;
    private EditText passwdEdit;
    private EditText passwdConfEdit;
    private EditText phoneEdit;
    private EditText emailEdit;
    private EditText nickEdit;
    private Button registerBtn;
    private Bitmap avatar;
    //private ImageView titleBackImg;
    //private TextView titleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_sign_in);
        initView();
        Bmob.initialize(this, appId);
        avatar= BitmapFactory.decodeResource(getResources(),R.drawable.sign_in_icon);
        //默认图片存储
        PhotoUtil.saveBitmap(BmobConstants.MyAvatarDir, filename, avatar,true);

    }

    private void initView() {
        cImg = (CircleImageView) findViewById(R.id.sign_in_cImg);
        updateTv = (TextView) findViewById(R.id.sign_in_update_tv);
        updateTv.setOnClickListener(this);
        /*titleBackImg= (ImageView) findViewById(R.id.title_back);
        titleBackImg.setOnClickListener(this);
        titleTv= (TextView) findViewById(R.id.title_tv);
        titleTv.setText(R.string.sign_in_title);*/
        userEdit = (EditText) findViewById(R.id.sign_in_user);
        passwdEdit = (EditText) findViewById(R.id.sign_in_passwd);
        passwdConfEdit = (EditText) findViewById(R.id.sign_in_passwd_conf);
        phoneEdit = (EditText) findViewById(R.id.sign_in_phone);
        nickEdit= (EditText) findViewById(R.id.sign_in_nickname);
        emailEdit = (EditText) findViewById(R.id.sign_in_email);
        registerBtn = (Button) findViewById(R.id.sign_in_register);
        registerBtn.setOnClickListener(this);
    }

    //使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileNmae() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_update_tv:
                new AlertDialog.Builder(SignInActivity.this).setTitle("选择方式")
                        .setIcon(R.drawable.umeng_socialize_share_video)
                        .setMessage("请选择相机或者相册")
                        .setPositiveButton("相机", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //激活相机
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //判断存储卡是否可以用, 若可用就进行存储
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                            }
                        }).setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //激活系统图库，选择一张图片
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        //开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                    }
                }).show();
                break;
            case R.id.sign_in_register:
                switch (checkAll()) {
                    case USER_NULL:
                        Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case PASSWD_NULL:
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case PASSW_CONF_NULL:
                        Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case MOBILE_PHONE_NUMBER_NULL:
                        Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case EMAIL_NULL:
                        Toast.makeText(this, "邮件不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case PASSWD_CONF_FAILED:
                        Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                        break;
                    case PASSWD_LENGTH_ERROR:
                        Toast.makeText(this, "密码长度应在6-20位之间", Toast.LENGTH_SHORT).show();
                        break;
                    case PHONE_NUMBER_ERROR:
                        Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                        break;
                    case NICK_NAME_NULL:
                        Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    case ALL_IS_OK:
                        progressDialog=new ProgressDialog(this);
                        SetProgressMsg("正在添加...");
                        ShowProgress();
                        signUpUser();//顺序如此是因为，bmob后端会帮我们检查手机和邮箱是否重复
                        break;
                }
                break;
            /*case R.id.title_back:
                Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;*/
            default:
                break;
        }

    }

    //用来检查各个输入框的输入
    private int checkAll() {
        user = userEdit.getText().toString();
        passwd = passwdEdit.getText().toString();
        passwdConf = passwdConfEdit.getText().toString();
        phone = phoneEdit.getText().toString();
        email = emailEdit.getText().toString();
        nick=nickEdit.getText().toString();
        if (user.length() < 1) {
            return USER_NULL;
        } else if (passwd.length() < 1) {
            return PASSWD_NULL;
        } else if (passwdConf.length() < 1) {
            return PASSW_CONF_NULL;
        } else if (phone.length() < 1) {
            return MOBILE_PHONE_NUMBER_NULL;
        } else if (email.length() < 1) {
            return EMAIL_NULL;
        } else if (!passwd.equals(passwdConf)) {
            return PASSWD_CONF_FAILED;
        } else if (passwd.length() < 6 || passwd.length() > 20) {
            return PASSWD_LENGTH_ERROR;
        } else if (phone.length() != 11) {
            return PHONE_NUMBER_ERROR;
        }else if(TextUtils.isEmpty(nick)){
            return NICK_NAME_NULL;
        }
        return ALL_IS_OK;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(file));
                break;
            case PHOTO_REQUEST_GALLERY:
                //做非空判断，当我们觉得不满意想重新裁剪的时候便不会报异常，下同
                if (data != null) {
                    startPhotoZoom(data.getData());
                } else {
                    Toast.makeText(SignInActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    avatar = data.getParcelableExtra("data");
                    PhotoUtil.saveBitmap(BmobConstants.MyAvatarDir,filename,avatar,false);
                    cImg.setImageBitmap(avatar);
                }
                /*
                try {
                    //将临时文件删除
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //调用系统裁剪功能
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", true);
        //aspectX aspectY 宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //outputX,outputY 裁剪后图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", "PNG");// 图片格式
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        System.out.println("22================");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void updateAvatar(){
        final BmobFile bmobFile=new BmobFile(file);
        bmobFile.uploadblock(this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                String url=bmobFile.getFileUrl(SignInActivity.this);
                ShowToast("上传头像成功！");
                updateUserUrl(url);
            }

            @Override
            public void onFailure(int i, String s) {
                DismissProgress();
            }
        });
    }
    //其中的id需要在updateUserUrl中使用
    User userInfo;
    private void signUpUser(){
        userInfo= new User();
        userInfo.setUsername(this.user);
        userInfo.setPassword(passwd);
        userInfo.setMobilePhoneNumber(phone);
        userInfo.setMobilePhoneNumberVerified(false);
        userInfo.setEmail(email);
        userInfo.setNickname(nick);
        userInfo.setEmailVerified(false);
        //user.setAvatarurl(url);
        userInfo.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ShowToast("添加用户数据成功，返回objectId" + userInfo.getObjectId());
                updateAvatar();
            }

            @Override
            public void onFailure(int i, String s) {
                DismissProgress();
                ShowToast("添加数据失败 " + s);

            }
        });
    }

    /*
    * 当用户的手机和邮件都没问题的时候，并且文件上传成功，才更新对应user的url
    * */
    private void updateUserUrl(String url){
        userInfo.setAvatarurl(url);
        userInfo.update(this, userInfo.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                ShowToast("注册成功！欢迎加入Fight社团^_^");
                DismissProgress();
            }

            @Override
            public void onFailure(int i, String s) {
                ShowToast("注册失败！原因"+s);
                DismissProgress();
            }
        });
    }
    @Override
    protected void onDestroy() {
        PhotoUtil.recycle(avatar);
        super.onDestroy();

    }
}
