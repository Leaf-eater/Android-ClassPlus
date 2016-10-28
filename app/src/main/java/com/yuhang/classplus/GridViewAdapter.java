package com.yuhang.classplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 宇航 on 2016/9/21.
 *
 */

/**GirdView 数据适配器*/
public class GridViewAdapter extends BaseAdapter {
    Context context;
    List<ClassRoom> list;
    public GridViewAdapter(Context _context, List<ClassRoom> _list) {
        this.list = _list;
        this.context = _context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.list_item, null);
//        TextView className = (TextView) convertView.findViewById(R.id.textView_classname);
//        TextView peopleNum = (TextView) convertView.findViewById(R.id.textView_studentNum);
//        ClassRoom classRoom = list.get(position);
//        className.setText(classRoom.getClassName());
//        peopleNum.setText(classRoom.getPeopleNum());
        return convertView;
    }
}

