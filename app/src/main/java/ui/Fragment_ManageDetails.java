package ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yuhang.classplus.HttpUtil;
import com.yuhang.classplus.R;

import org.apache.http.Header;

import java.util.HashMap;

import widget.URL;

/**
 * Created by 宇航 on 2016/10/14.
 *
 */

public class Fragment_ManageDetails extends Fragment implements View.OnClickListener{
    private TextView startTime;
    private TextView endTime;
    private TextView applyPerson;
    private TextView applyClass;
    private TextView applyReason;
    private Button bt_agree;
    private Button bt_disagree;
    private String uuid;
    private String url = URL.URL_APPROVE+"?applyId="+uuid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maneage_details, container,false);
        init(rootView);
        Bundle bundle = getArguments();
        HashMap data = (HashMap) bundle.get("applyInfo");
        analysisData(data);     //对控件进行赋值
        bt_agree.setOnClickListener(this);
        bt_disagree.setOnClickListener(this);
        return rootView;
    }

    /**
     * 将数据进行解析并赋值给响应的控件
     *data: {name=user123, endTime=16:00, startTime=14:00, className=G203, applyReason=play, uuid=A791BD65AC734BC6BA571A57C07BE4C9}
     * @param data ArrayList数组
     */
    private void analysisData(HashMap data) {
        Log.i("data",data.toString());
        startTime.setText((String) data.get("startTime"));
        endTime.setText((String) data.get("endTime"));
        applyPerson.setText((String)data.get("name"));
        applyReason.setText((String) data.get("applyReason"));
        applyClass.setText((String) data.get("className"));
        uuid = (String) data.get("uuid");
        Log.i("uuid", "uuid:" + uuid);
    }

    private void init(View rootView) {
        startTime = (TextView) rootView.findViewById(R.id.textView_manageDetails_startTime);
        endTime = (TextView) rootView.findViewById(R.id.textView_manageDetails_endTime);
        applyPerson = (TextView) rootView.findViewById(R.id.textView_manageDetails_applyPerson);
        applyClass = (TextView) rootView.findViewById(R.id.textView_manageDetails_applyClass);
        applyReason = (TextView) rootView.findViewById(R.id.textView_manageDetails_applyReason);
        bt_agree = (Button) rootView.findViewById(R.id.button_manageDetails_agree);
        bt_disagree = (Button) rootView.findViewById(R.id.button_managedetails_disagree);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_manageDetails_agree:
                HttpUtil.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        if(i==200){
                            Toast.makeText(getActivity(), "管理已批准", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"请求格式有误"+i,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(getActivity(),"网络异常"+i,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.button_managedetails_disagree:
                HttpUtil.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        if(i==200){
                            Toast.makeText(getActivity(), "管理已驳回", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"请求格式有误"+i,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(getActivity(),"网络异常"+i,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
