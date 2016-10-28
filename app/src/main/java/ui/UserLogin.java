package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yuhang.classplus.HttpUtil;
import com.yuhang.classplus.MainActivity;
import com.yuhang.classplus.R;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import widget.MD5;
import widget.Sts;
import widget.URL;

/**
 * Created by 宇航 on 2016/8/9.
 */
public class UserLogin extends Activity implements View.OnClickListener {
    JSONObject code;
    private EditText login_username;
    private EditText login_password;
    private Button user_login_button;
    private TextView user_register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.user_login);
        initWidget();

    }

    /**
     * 控件初始化
     */
    private void initWidget() {
        login_username = (EditText) findViewById(R.id.logIn_userNameText);
        login_password = (EditText) findViewById(R.id.logIn_passwdText);
        user_login_button = (Button) findViewById(R.id.bt_Login);
        user_register_button = (TextView) findViewById(R.id.logIn_skip_registration);
        user_login_button.setOnClickListener(this);
        user_register_button.setOnClickListener(this);
        login_username.setText("18716028916");
        login_password.setText("123456");
        login_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String username = login_username.getText().toString().trim();
                    if (username.length() < 4) {
                        Toast.makeText(UserLogin.this, "用户名不能小于4个字符", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        login_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = login_password.getText().toString().trim();
                    if (password.length() < 4) {
                        Toast.makeText(UserLogin.this, "密码不能小于4个字符", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }

    /**
     * 点击跳转发送登录请求或者跳转到注册页面
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Login:
                if (checkEdit()) {
                    login();
                }

                break;
            case R.id.logIn_skip_registration:
                Intent intent2 = new Intent(UserLogin.this, UserRegister.class);
                startActivity(intent2);
                break;
        }
    }

    /**
     * 检查密码是否为空
     * @return false if null
     */

    private boolean checkEdit() {
        if (login_username.getText().toString().trim().equals("")) {
            Toast.makeText(UserLogin.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (login_password.getText().toString().trim().equals("")) {
            Toast.makeText(UserLogin.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }


    /**
     * 发送登录请求，成功后传出用户信息um,bundle 中键值对为 um--JSONObject
     */
    private void login() {
        String url = URL.URL_LOGIN;
        JSONObject jso = new JSONObject();
        try {
            jso.put("phone", login_username.getText().toString().trim());
            jso.put("pwd", MD5.md5(login_password.getText().toString().trim()));
            Log.i("response", MD5.md5(login_password.getText().toString().trim()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity entity = null; //需要发送的数据
        AsyncHttpClient client = new AsyncHttpClient();
        try {
            entity = new StringEntity(jso.toString());
            entity.setContentType("application/json");
            entity.setContentEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("response", "entity:" + entity + "url" + url);
        HttpUtil.post(UserLogin.this, url, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast.makeText(UserLogin.this, "success"+i, Toast.LENGTH_SHORT).show();
                String content = new String(bytes);
                 try {
//                   JSONObject中包括umJSONObject,其中包含name,user,stuNum,drom,access
                     JSONObject jsonObject = new JSONObject(content);
                     Integer status = jsonObject.getInt("status");
                     JSONObject object = jsonObject.getJSONObject("um");
                     Log.i("response", "status" + status + "name");
                     if (Objects.equals(status, Sts.USER_LOGIN_SUCCESS)) {
                         Intent intent = new Intent(UserLogin.this, MainActivity.class);
                         intent.putExtra("um", object.toString());
                         Log.i("response", "startReady");
                         startActivity(intent);
                     } else {
                         Toast.makeText(UserLogin.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                     }
                 } catch (JSONException e)
                 {
                     e.printStackTrace();
                 }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(UserLogin.this, "failed"+i, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
