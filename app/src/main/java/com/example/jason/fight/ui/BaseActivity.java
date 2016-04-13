package com.example.jason.fight.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Jason on 2016/4/10.
 */
public class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    Toast mToast;
    public void ShowToast(final String text){
        if(!TextUtils.isEmpty(text)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mToast==null){
                        mToast=Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
                    }else{
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });
        }
    }

    ProgressDialog progressDialog;
    public void SetProgressMsg(String msg){
        if(progressDialog!=null){
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }
    public void ShowProgress(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }
    public void DismissProgress(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
