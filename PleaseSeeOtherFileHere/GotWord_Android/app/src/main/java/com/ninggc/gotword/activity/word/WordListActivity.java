package com.ninggc.gotword.activity.word;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ninggc.gotword.R;
import com.ninggc.gotword.activity.BaseActivity;
import com.ninggc.gotword.entity.Word;
import com.ninggc.gotword.util.CallServer;
import com.ninggc.gotword.util.Constant;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.util.List;

/**
 *
 * @author Ning
 * @date 12/5/2017 0005
 */

public class WordListActivity extends BaseActivity {
    RecyclerView recyclerView;
    WordListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_recyclerview);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                syncList();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordListAdapter(this, null);
        recyclerView.setAdapter(adapter);
        syncList();
    }

    private void syncList() {
        if(!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }

        Request<String> request = NoHttp.createStringRequest(Constant.url + "word/selectByGroup.php", RequestMethod.POST);
        request.set("word_group_id", String.valueOf(getIntent().getIntExtra("id", 0)));
        CallServer.getInstance().add(0, request, new SimpleResponseListener() {

            @Override
            public void onSucceed(int what, Response response) {
                if (response.responseCode() == 200) {
                    String s = (String) response.get();
                    Log.e("NOHTTP", "onSucceed: " + s);
                    JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                    String json = String.valueOf(jsonObject.get("data"));
                    List<Word> words = gson.fromJson(json, new TypeToken<List<Word>>(){}.getType());
                    adapter.addItem(words);
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                Toast.makeText(WordListActivity.this, "异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {
                Log.e("NOHTTP", "onFinish: " + gson.toJson(adapter.words));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
