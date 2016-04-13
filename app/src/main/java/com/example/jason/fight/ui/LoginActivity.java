package com.example.jason.fight.ui;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.fight.R;
import com.example.jason.fight.bean.User;

import cn.bmob.v3.listener.SaveListener;

//需要考虑Activity的启动模式
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView loginImg;
    private EditText loginUser,loginPwd;
    private Button loginBtn;
    private TextView loginFindPwd,loginSignUp;
   // private ImageView titleBackImg;
   // private TextView titleTv;

    private String username;
    private String passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //initActionBar();
    }
    private void initView(){
        loginImg= (ImageView) findViewById(R.id.login_image);
        loginBtn= (Button) findViewById(R.id.login_login_btn);
        loginBtn.setOnClickListener(this);
        loginUser= (EditText) findViewById(R.id.login_user);
        loginPwd= (EditText) findViewById(R.id.login_passwd);
        loginFindPwd= (TextView) findViewById(R.id.login_find_pwd);
        loginSignUp= (TextView) findViewById(R.id.login_sign_up);
        /*titleBackImg= (ImageView) findViewById(R.id.title_back);
        titleTv= (TextView) findViewById(R.id.title_tv);
        titleTv.setText(R.string.login_title);
        titleBackImg.setOnClickListener(this);*/
    }
    private void initActionBar(){
        ActionBar actionBar=getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.back:
                        ShowToast("hehe");
                        break;
                }
            }
        });

    }
    @Override
    public void onClick(View v) {
        username=loginUser.getText().toString();
        passwd=loginPwd.getText().toString();
        switch (v.getId()){
            case R.id.login_login_btn:
                if(TextUtils.isEmpty(username)){
                    loginUser.setError(getString(R.string.login_error_empty_user));
                    loginUser.requestFocus();
                }else if(TextUtils.isEmpty(passwd)){
                    loginPwd.setError(getString(R.string.login_error_empty_passwd));
                    loginPwd.requestFocus();
                }else {
                    //与服务器进行交互 数据
                    progressDialog=new ProgressDialog(this);
                    SetProgressMsg("正在登入...");
                    ShowProgress();
                    login();

                }
                break;
            case R.id.login_find_pwd:
                //打开网页进行密码找回
                ShowToast("跳转找回密码页面！");
                break;
            case R.id.login_sign_up:
                //打开网页进行注册
                Intent intent=new Intent(LoginActivity.this,SignInActivity.class);
                startActivity(intent);

                break;
            /*case R.id.title_back:
                Intent intent1=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;*/
            default:
                break;
        }
    }
    private void login(){
        //进行服务器
        final User user=new User();
        user.setUsername(username);
        user.setPassword(passwd);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ShowToast("登入成功！跳转中...");
                DismissProgress();
                //Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                //intent.putExtra("userId",us)
                ShowToast(user.getObjectId());
            }

            @Override
            public void onFailure(int i, String s) {
                ShowToast("登入失败！ "+s);
                DismissProgress();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
