package com.ninggc.gotword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ninggc.gotword.R;
import com.ninggc.gotword.activity.group.CreateGroupActivity;
import com.ninggc.gotword.activity.user.LoginActivity;
import com.ninggc.gotword.entity.Group;
import com.ninggc.gotword.util.CallServer;
import com.ninggc.gotword.util.Constant;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Group> groups;
    RecyclerView recyclerView;
    GroupListAdapter adapter;
    LinearLayout layout_user;
    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    // FIXME: 12/8/2017 0008 登录模块待添加
    private String user_id = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, CreateGroupActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
        layout_user = (LinearLayout) view.getHeaderView(0).findViewById(R.id.layout_user);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                syncList(user_id);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        adapter = new GroupListAdapter(this, groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        syncList("test");
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Constant.CODE_LOGIN);
            }
        });
    }

    private void syncList(String user_id) {
        if(!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }

        if (user_id == null || "".equals(user_id)) {
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        Request<String> request = NoHttp.createStringRequest(Constant.url + "group/selectByUser.php", RequestMethod.POST);
        request.set("user_id", "1");
        CallServer.getInstance().add(0, request, new SimpleResponseListener() {
            @Override
            public void onStart(int what) {
                super.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response response) {
                if (response.responseCode() == 200) {
                    String s = (String) response.get();
                    Log.e("NOHTTP", "onSucceed: " + s);
                    JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                    String json = String.valueOf(jsonObject.get("data"));
                    List<Group> groups = gson.fromJson(json, new TypeToken<List<Group>>(){}.getType());
                    adapter.changeList(groups);
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                Toast.makeText(MainActivity.this, "异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {
                Log.e("NOHTTP", "onFinish: " + gson.toJson(adapter.groups));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case Constant.CODE_LOGIN:
                String user_id = data.getStringExtra("username");
                syncList(user_id);
                break;
            default:
                break;
        }
    }
}
