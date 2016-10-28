package ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yuhang.classplus.MainActivity;
import com.yuhang.classplus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宇航 on 2016/9/24.
 * Home_page Fragment
 */

public class Fragment_HomePage extends Fragment implements AdapterView.OnItemClickListener{
    ImageView imageView_news;
    ListView listView1;
    ListView listView2;
    ListView listView3;
    ListView listView4;

    //教室名
    private String[] className_ListView1 = {"G103","G212","G222","G103","G212","G222","G103","G212","G222"};
    private String[] className_ListView2 = {"G303","G312","G403"};
    private String[] className_ListView3 = {"G513","G514","G603"};
    private String[] className_ListView4 = {"G703","G802","G805"};
    //教室状态
    private String[] classState_ListView1 = {"空闲","上课","空闲","空闲","上课","空闲","空闲","上课","空闲"};
    private String[] classState_ListView2 = {"空闲","上课","空闲"};
    private String[] classState_ListView3 = {"空闲","上课","空闲"};
    private String[] classState_ListView4 = {"空闲","上课","空闲"};
    //剩余人数
    private String[] studentNum_ListView1 = {"10","30","7","10","30","7","10","30","7"};
    private String[] studentNum_ListView2 = {"7","30","11"};
    private String[] studentNum_ListView3 = {"0","33","8"};
    private String[] studentNum_ListView4 = {"13","28","6"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container,false);
        Log.i("test","HomePageFragment createView");
        init(rootView);
//  设置图片的点击事件
        imageView_news = (ImageView) rootView.findViewById(R.id.imageView_HomePageNews);
        imageView_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri url = Uri.parse("http://www.neuq.edu.cn/info/1103/2280.htm");
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(intent);
            }
        });

//      填充listView
        inflateListView(listView1,className_ListView1,classState_ListView1,studentNum_ListView1);
        inflateListView(listView2,className_ListView2,classState_ListView2,studentNum_ListView2);
        inflateListView(listView3,className_ListView3,classState_ListView3,studentNum_ListView3);
        inflateListView(listView4,className_ListView4,classState_ListView4,studentNum_ListView4);
        return rootView;
    }

    private void inflateListView(ListView listView ,String[] className_ListView ,String[] classState_ListView, String[] studentNum_ListView){
        List<Map<String,Object>> listContent = new ArrayList<>();
        for(int i = 0;i<className_ListView.length;i++){
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("className",className_ListView[i]);
            listItem.put("state", classState_ListView[i]);
            listItem.put("stuNum", studentNum_ListView[i]);
            listItem.put("test", "test");
            listContent.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.getinstance(), listContent, R.layout.list_item,
                new String[]{"className", "state", "stuNum"}, new int[]{R.id.list_item_className, R.id.list_item_state, R.id.list_item_stu_num});
        listView.setAdapter(simpleAdapter);
    }

    private void init(View rootView) {
        listView1 = (ListView) rootView.findViewById(R.id.listView_class_1);
        listView1.setOnItemClickListener(this);
        listView2 = (ListView) rootView.findViewById(R.id.listView_class_2);
        listView2.setOnItemClickListener(this);
        listView3 = (ListView) rootView.findViewById(R.id.listView_class_3);
        listView3.setOnItemClickListener(this);
        listView4 = (ListView) rootView.findViewById(R.id.listView_class_4);
        listView4.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment_ClassInfo fragmentNew_ClassInfo  = new Fragment_ClassInfo();
        Bundle bundle = new Bundle();
        String[] classNames = (String[]) getData((ListView) parent).get(0);
        String[] classStates = (String[]) getData((ListView) parent).get(1);
        String[] studentNums = (String[]) getData((ListView) parent).get(2);
        String className = classNames[position];
        String classState = classStates[position];
        String studentNum = studentNums[position];
        bundle.putSerializable("className",className);
        bundle.putSerializable("classState",classState);
        bundle.putSerializable("studentNum",studentNum);
        fragmentNew_ClassInfo.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayout_content, fragmentNew_ClassInfo);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * 将数据数组与list相结合
     * @param listView
     * @return 返回list中0为className,1为classState,2为studentNum
     */
    private List getData(ListView listView) {
        List<String[]> list = new ArrayList<>();
//        将数据数组放入对应的listView对象中
        if(listView==listView1){
            list.add(0,className_ListView1);
            list.add(1,classState_ListView1);
            list.add(2,studentNum_ListView1);
        }else if(listView==listView2){
            list.add(0,className_ListView2);
            list.add(1,classState_ListView2);
            list.add(2,studentNum_ListView2);
        }else if(listView==listView3){
            list.add(0,className_ListView3);
            list.add(1,classState_ListView3);
            list.add(2,studentNum_ListView3);
        }else if(listView==listView4){
            list.add(0,className_ListView4);
            list.add(1,classState_ListView4);
            list.add(2,studentNum_ListView4);
        }else {
            Log.i("Fragment_HomePage", "没有找到list对象，添加失败");
        }
        return list;
    }

}
