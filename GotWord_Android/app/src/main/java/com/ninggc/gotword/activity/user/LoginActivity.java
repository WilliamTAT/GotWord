package com.ninggc.gotword.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ninggc.gotword.R;
import com.ninggc.gotword.activity.BaseActivity;
import com.ninggc.gotword.util.CallServer;
import com.ninggc.gotword.util.Constant;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

/**
 *
 * @author Ning
 * @date 12/7/2017 0007
 */

public class LoginActivity extends BaseActivity {
    private TextInputLayout til_account;
    private TextInputLayout til_password;
    private Button btn_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_login);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        super.initView();

        til_account = (TextInputLayout) findViewById(R.id.login_til_account);
        til_password = (TextInputLayout) findViewById(R.id.login_til_password);
        btn_login = (Button) findViewById(R.id.login_btn_login);

    }

    @Override
    protected void initData() {
        super.initData();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = til_account.getEditText().getText().toString();
                String password = til_password.getEditText().getText().toString();

                Request<String> request = NoHttp.createStringRequest(Constant.url + "user/login.php", RequestMethod.POST);
                request.set("username", username);
                request.set("password", password);
                CallServer.getInstance().add(0, request, new SimpleResponseListener() {
                    @Override
                    public void onSucceed(int what, Response response) {
                        super.onSucceed(what, response);

                        try {
                            String json = (String) response.get();
                            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                            String result = jsonObject.get("result").getAsString();
                            if("false".equals(result)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Log.e("NOHTTP", "onSucceed: " + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "登录异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } finally {
                            setResult(Constant.CODE_LOGIN, new Intent().putExtra("username", username));
                            LoginActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFailed(int what, Response response) {
                        super.onFailed(what, response);
                    }
                });
            }
        });
    }
}
