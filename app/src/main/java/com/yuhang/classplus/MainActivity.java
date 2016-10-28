package com.yuhang.classplus;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ui.Fragment_ApplyPage;
import ui.Fragment_HomePage;
import ui.Fragment_ManagePage;

/**
 * 提供getinstence()方法在非Activity中获取context
 *
 */
public class MainActivity extends Activity implements View.OnClickListener{
    public static MainActivity instance;
    private Button mButtonToHomePage;
    private Button mButtonToApplyPage;
    private Button mButtonToManagePage;
    private Fragment_HomePage fragment_homePage;
    private Fragment_ApplyPage fragment_applyPage;
    private Fragment_ManagePage fragment_managePage;
    public User current_user;

    public static MainActivity getinstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("mainActivity", "startSuccess");
        instance = this;
        initWidget();
        setUser();
        setDefaultFragment();
    }

    /**
     * 绑定当前使用的用户
     */
    private void setUser()  {
        Log.i("mainActivity", "coming set user");
        Intent intent= getIntent();
        Log.i("mainActivity", "get intent");
        current_user = new User();
        if (intent.getExtras()!=null) {
//            Bundle data = intent.getExtras();
//            String userInfo_string = data.getString("um");
            String userInfo_string = intent.getStringExtra("um");
            try {
                JSONObject userInfo;
                userInfo = new JSONObject(userInfo_string);
                userInfo.getString("access");
                current_user.setAccess(userInfo.getString("access"));
                current_user.setDrom(userInfo.getString("drom"));
                current_user.setName(userInfo.getString("name"));
// todo               current_user.setPhone(userInfo.getString("phone")); 由于没有phoneNum的返回，所以这里应该做一个默认参数的设置
                current_user.setStuNum(userInfo.getString("stuNum"));
            }
            catch (JSONException e) {
                Toast.makeText(this, "JSON解析异常", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public User getUser(){
        return current_user;
    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragment_homePage = new Fragment_HomePage();
        ft.replace(R.id.FrameLayout_content,fragment_homePage);
        ft.commit();
    }

    private void initWidget() {
        mButtonToHomePage = (Button) findViewById(R.id.button_home_page);
        mButtonToApplyPage = (Button) findViewById(R.id.button_apply);
        mButtonToManagePage = (Button) findViewById(R.id.button_manage);
        mButtonToHomePage.setOnClickListener(this);
        mButtonToApplyPage.setOnClickListener(this);
        mButtonToManagePage.setOnClickListener(this);
    }

    /**
     * 设置点击启动的Fragment
     * @param v 指当前Activity
     */
    @Override
    public void onClick(View v) {
        Log.i("test","coming here onClickListener");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.button_home_page:
                fragment_homePage = new Fragment_HomePage();
                ft.replace(R.id.FrameLayout_content,fragment_homePage);
                Toast.makeText(this,"clicked button_home_page",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_apply:
                fragment_applyPage = new Fragment_ApplyPage();
                ft.replace(R.id.FrameLayout_content,fragment_applyPage);
                Toast.makeText(this,"clicked button_apply",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_manage:
                fragment_managePage = new Fragment_ManagePage();
                ft.replace(R.id.FrameLayout_content,fragment_managePage);
                Toast.makeText(this,"clicked button_manage",Toast.LENGTH_SHORT).show();
                break;
        }
        ft.commit();
    }

}
