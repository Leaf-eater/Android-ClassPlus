package com.yuhang.testmodule;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity{
    private ListView lv;
    private ArrayList<String> list = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                getData());
        lv.setAdapter(adapter);

    }

    private ArrayList<String> getData()
    {
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        return list;
    }
}
