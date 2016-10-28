package ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yuhang.classplus.HttpUtil;
import com.yuhang.classplus.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import widget.URL;

/**
 * Created by 宇航 on 2016/9/24.
 * Manage page Fragment
 */

public class Fragment_ManagePage extends Fragment{
    private ListView listView_manage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_page, container, false);
        init(rootView);
        getData();      //获取数据并启动fragmentManageDetails
        return rootView;
    }

    private void init(View rootView) {
        listView_manage = (ListView) rootView.findViewById(R.id.listView_manage);
    }

    /**
     * 获取得到的数据，成功后填充listView,并将'applyInfo'在onItemClick事件中传出
     */
    public void getData() {
        String url = URL.URL_QUERY_APPLY;   //查询申请信息
        HttpUtil.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
//              设置listView
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                        analysisData(response),
                        R.layout.list_item,
                        new String[]{"className", "startTime", "endTime"},
                        new int[]{R.id.list_item_className, R.id.list_item_state, R.id.list_item_stu_num});
                listView_manage.setAdapter(adapter);

//               在onclick方法中启动并将信息传送到新的fragment（包括className与所有信息的列表）
                listView_manage.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                      获取应该传输的，当前点击Item对应的数据
                        String ItemClassName;   //点击Item中的className
                        Bundle bundle = new Bundle();
                        LinearLayout layout = (LinearLayout) view;      //获取点击的LinearLayout
                        ItemClassName = ((TextView) layout.findViewById(R.id.list_item_className)).getText().toString();
//                      将对应的数据放入bundle并发送
                        bundle.putSerializable("applyInfo",getItemData(response,ItemClassName));
                        Fragment_ManageDetails newFragment = new Fragment_ManageDetails();
                        newFragment.setArguments(bundle);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.FrameLayout_content, newFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
//                  获取点击item的数据
                    private HashMap getItemData(JSONArray response, String itemClassName) {
                        HashMap<String, String> data = new HashMap<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getString("className").equals(itemClassName)) {
                                    data.put("uuid", object.getString("uuid"));
                                    data.put("startTime", object.getString("startTime"));
                                    data.put("endTime", object.getString("endTime"));
                                    data.put("className", object.getString("className"));
                                    data.put("applyReason", object.getString("applyReason"));
                                    data.put("name", object.getJSONObject("um").getString("name"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return data;
                    }

                });
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //解析传入的数据并以list集合的形式返回

    /**
     * @param response jSON数组
     * @return List集合，集合中将包含所有已申请的信息
     */
    private ArrayList analysisData(JSONArray response) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            Map<String,String> map = new HashMap<>();
            try {
                JSONObject jsonObject = response.getJSONObject(i);  //获取jsonObject对象
                map.put("className", jsonObject.getString("className"));
                map.put("startTime", jsonObject.getString("startTime"));
                map.put("endTime", jsonObject.getString("endTime"));
                map.put("uuid", jsonObject.getString("uuid"));
                map.put("applyReason", jsonObject.getString("applyReason"));
                map.put("applyPerson", jsonObject.getJSONObject("um").getString("name"));
                list.add(map);
            } catch (JSONException e) {
                Log.i("managePage", "JSON数据解析异常");
                e.printStackTrace();
            }
        }
        return list;
    }


}
