package com.ninggc.gotword.activity.group;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ning on 12/8/2017 0008.
 */

public class CreateGroupActivity extends BaseActivity implements View.OnClickListener {
    private TextInputLayout til_group_name;
    private LinearLayout linearLayout;
    private EditText et_word;
    private Button btn_add;
    private Button btn_finish;
    List<EditText> editTexts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_group);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();

        til_group_name = (TextInputLayout) findViewById(R.id.create_group_til_name);
        et_word = (EditText) findViewById(R.id.create_group_et_word);
        editTexts.add(et_word);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_words);
        btn_add = (Button) findViewById(R.id.create_group_btn_add);
        btn_finish = (Button) findViewById(R.id.create_group_btn_finish);
    }

    @Override
    protected void initData() {
        super.initData();

        btn_add.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_group_btn_add:
                addWordEditText();
                break;
            case R.id.create_group_btn_finish:
                createGroup();
                break;
            default:break;
        }
    }

    private void createGroup() {
        String groupName = til_group_name.getEditText().getText().toString();
        if (groupName == null || "".equals(groupName)) {
            Toast.makeText(this, "请输入组名", Toast.LENGTH_SHORT).show();
        }
        List<String> words = new ArrayList<>();
        for (EditText et : editTexts) {
            String s = et.getText().toString();
            if(s == null || "".equals(s)) {
                continue;
            }
            words.add(s);
        }
        if (words.size() == 0) {
            Toast.makeText(this, "请至少输入一个单词", Toast.LENGTH_SHORT).show();
            return;
        }

        Request<String> request = NoHttp.createStringRequest(Constant.url + "group/createGroup.php", RequestMethod.POST);
        request.set("group_name", groupName);
        request.set("user_id", "1");
        request.set("username", "ninggc");
        request.set("words", gson.toJson(words));
        CallServer.getInstance().add(0, request, new SimpleResponseListener() {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                String s = (String) response.get();
                JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                if (jsonObject.get("result").getAsInt() == 1) {
                    Toast.makeText(CreateGroupActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateGroupActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                super.onFailed(what, response);
            }

            @Override
            public void onFinish(int what) {
                super.onFinish(what);
            }
        });
    }

    private void addWordEditText() {
        EditText et = new EditText(this);
        linearLayout.addView(et);
        editTexts.add(et);
    }
}
