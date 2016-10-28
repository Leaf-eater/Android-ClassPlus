package ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yuhang.classplus.HttpUtil;
import com.yuhang.classplus.R;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Objects;

import widget.URL;

import static android.R.attr.name;
import static widget.MD5.md5;

/**
 * Created by 宇航 on 2016/8/9.
 *
 */
public class UserRegister extends Activity{
    private EditText register_username;
    private EditText register_passwd;
    private EditText reregister_passwd;
    private EditText register_phoneNum;
    private EditText register_stuNum;
    private RadioGroup register_group;
    private String drom;
    private RadioButton register_selsct_x;
    private RadioButton register_select_p;
    private Button register_submit;
    private Button register_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.user_register);
        initData();
        /**
         * 一个EditText焦点变化时触发字符检测
         * 用户名检测
         */
        register_username.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    if(register_username.getText().toString().trim().length()<4){
                        Toast.makeText(UserRegister.this, "用户名不能小于4个字符", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        /**
         * 密码强度检测
         */
        register_passwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    if(register_passwd.getText().toString().trim().length()<6){
                        Toast.makeText(UserRegister.this, "密码不能小于8个字符", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        /**
         * 密码重复检测
         */
        reregister_passwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    if(!reregister_passwd.getText().toString().trim().equals(register_passwd.getText().toString().trim())){
                        Toast.makeText(UserRegister.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        /**
         * 单选框检测
         * 鹏远为1 校内为0 六号楼为2
         */
        register_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                if (register_select_p.getId() == radioButtonId) {
                    drom = "2";
                }else if(register_selsct_x.getId() == radioButtonId){
                    drom = "0";
                }
            }
        });
        /**
         * 提交事件&&储存数据
         */
        register_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkEdit()) {
                    return;
                }
                String url = URL.URL_REGISTER;
                Log.i("register", url);
//                提交并数据
                HttpUtil.post(UserRegister.this, url, getEntity(), "application/json", new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                      存储数据
                        saveData();
//                      获取响应并跳转
                        Log.i("response", "name" + name + "state" + i);
                            if (Objects.equals(i, 200)) {
                                Intent intent = new Intent(UserRegister.this, UserLogin.class);
                                startActivity(intent);
                            }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(UserRegister.this, "register failed : internet error", Toast.LENGTH_SHORT).show();
                        Log.i("register","int:"+i+"header"+ Arrays.toString(headers) +"bytes"+bytes);
                    }
                });
            }

//           非空检测
            private boolean checkEdit() {
                if (register_username.getText().toString().trim().equals("")) {
                    Toast.makeText(UserRegister.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (register_passwd.getText().toString().trim().equals("")) {
                    Toast.makeText(UserRegister.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!register_passwd.getText().toString().trim().equals(reregister_passwd.getText().toString().trim())) {
                    Toast.makeText(UserRegister.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                } else if (register_stuNum.getText().toString().trim().equals("")) {
                    Toast.makeText(UserRegister.this, "学号不能为空", Toast.LENGTH_SHORT).show();
                }else if (register_phoneNum.getText().toString().trim().equals("")) {
                    Toast.makeText(UserRegister.this, "电话不能为空", Toast.LENGTH_SHORT).show();
                } else{
                    return true;
                }
                return false;
            }

//           存储数据 ,包括
//           name, phoneNmu , studentNum , drom , pwd
            private void saveData(){
                SharedPreferences preferences = getSharedPreferences(register_phoneNum.getText().toString().trim(), MODE_ENABLE_WRITE_AHEAD_LOGGING);
                preferences.edit().putString("name", register_username.getText().toString().trim());
                preferences.edit().putString("phoneNum", register_phoneNum.getText().toString().trim());
                preferences.edit().putString("studentNum", register_stuNum.getText().toString().trim());
                preferences.edit().putString("drom", drom);
                preferences.edit().putString("pwd",md5(register_passwd.getText().toString().trim()));
                preferences.edit().commit();
            }

            private StringEntity getEntity(){
                JSONObject json = new JSONObject();
                String pwd = md5(register_passwd.getText().toString().trim());
                try {
                    json.put("name", register_username.getText().toString().trim());
                    json.put("pwd", pwd);
                    json.put("phone",register_phoneNum.getText().toString().trim());
                    json.put("stuNum",register_stuNum.getText().toString().trim());
                    json.put("drom",drom);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("JSON", "数据转换异常");
                }
                StringEntity entity = null; //需要发送的数据
                try {
                    entity = new StringEntity(json.toString(),"utf-8");
                    entity.setContentType("application/json");
                    entity.setContentEncoding("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return entity;
            }

        });

        /**
         * 返回登录页面
         */
        register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegister.this,UserLogin.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        register_username=(EditText)findViewById(R.id.register_username);
        register_passwd=(EditText)findViewById(R.id.register_passwd);
        reregister_passwd=(EditText)findViewById(R.id.reregister_passwd);
        register_submit=(Button)findViewById(R.id.register_submit);
        register_phoneNum = (EditText) findViewById(R.id.register_PhoneNumber);
        register_group = (RadioGroup) findViewById(R.id.register_RadioGroup);
        register_selsct_x = (RadioButton) findViewById(R.id.register_select_x);
        register_select_p = (RadioButton) findViewById(R.id.register_select_p);
        register_stuNum = (EditText) findViewById(R.id.register_studentNumber);
        register_return = (Button) findViewById(R.id.bt_return_to_logIn);
        register_username.setText("user123");
        register_phoneNum.setText("18716028916");
        register_stuNum.setText("2145123");
        register_passwd.setText("123456");
        reregister_passwd.setText("123456");
    }

}