package com.ninggc.gotword.activity.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Ning on 12/8/2017 0008.
 */

public class CreateGroupActivity extends BaseActivity implements View.OnClickListener {
    private TextInputLayout tilGroupName;
    private EditText etInputWord;
    private TextView tvShow;
    private Button btnFormat;
    private Button btnFinish;
    Set<String> words = new HashSet<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_group);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();

        tilGroupName = (TextInputLayout) findViewById(R.id.create_group_til_name);
        tvShow = (TextView) findViewById(R.id.create_group_tv_show);
        etInputWord = (EditText) findViewById(R.id.create_group_et_word);
        btnFormat = (Button) findViewById(R.id.create_group_btn_format);
        btnFinish = (Button) findViewById(R.id.create_group_btn_finish);
    }

    @Override
    protected void initData() {
        super.initData();

        btnFormat.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_group_btn_format:
                formatInputWords();
                break;
            case R.id.create_group_btn_finish:
                createGroup();
                break;
            default:break;
        }
    }

    private void createGroup() {
        String groupName = tilGroupName.getEditText().getText().toString();
        if (groupName == null || "".equals(groupName)) {
            Toast.makeText(this, "请输入组名", Toast.LENGTH_SHORT).show();
            return;
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

    private void formatInputWords() {
        String s = etInputWord.getText().toString();
        String[] split = s.split(" ");
        Collections.addAll(words, split);
        if (words.contains(" ")) {
            words.remove(" ");
        }
        StringBuilder builder = new StringBuilder().append("已输入单词: \n");
        List<String> list = new ArrayList<>(words);
        Collections.sort(list);
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()) {
            builder.append("\"").append(iterator.next()).append("\"").append("-");
        }
        tvShow.setText(builder.substring(0, builder.length() - 1));
        etInputWord.setText("");
    }
}
