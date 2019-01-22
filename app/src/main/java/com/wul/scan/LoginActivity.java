package com.wul.scan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wul.scan.api.HttpResultSubscriber;
import com.wul.scan.api.HttpServiceIml;
import com.wul.scan.data.LoginBO;
import com.wul.scan.data.UserBO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.login)
    TextView login;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String strCode = editCode.getText().toString().trim();
        String strPassWord = editPassword.getText().toString().trim();
        if (StringUtils.isEmpty(strCode)) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (StringUtils.isEmpty(strPassWord)) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        LoginBO loginBO = new LoginBO();
        loginBO.username = strCode;
        loginBO.password = MD5.strToMd5Low32(MD5.strToMd5Low32(strPassWord) + "bby");
        HttpServiceIml.login(loginBO).subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO s) {
                MyApplication.token = s.getToken();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFiled(String message) {
                ToastUtils.showShort(message);
            }
        });
    }
}
