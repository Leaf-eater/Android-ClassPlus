package com.yuhang.classplus;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by 宇航 on 2016/8/10.
 *
 */
public class DataManager extends Activity{
    private String fileName;
    DataManager instance;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
    }


    public DataManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void saveData(String name, String data){
        editor = preferences.edit();
        editor.putString(name,data);
        editor.commit();
    }

    public String getData(String fileName){
        return preferences.getString(fileName,"no value");
    }

}
