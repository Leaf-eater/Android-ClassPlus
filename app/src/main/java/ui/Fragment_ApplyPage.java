package ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yuhang.classplus.HttpUtil;
import com.yuhang.classplus.MainActivity;
import com.yuhang.classplus.R;
import com.yuhang.classplus.User;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import widget.URL;

/**
 * Created by 宇航 on 2016/9/24.
 * Apply fragment
 */

public class Fragment_ApplyPage extends Fragment{
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextClassName;
    private EditText editTextApplyReason;
    private Button buttonCommit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_apply_page, container,false);
        init(rootView);

        User user = ((MainActivity) getActivity()).getUser();
        final String url = URL.URL_APPLY + user.getAccess() + "/apply.do";
        buttonCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getEntity();
                HttpUtil.post(getActivity(), url, getEntity(), "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Toast.makeText(getActivity(), "apply success" + i, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                        Toast.makeText(getActivity(),"apply failed"+i,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private StringEntity getEntity() {
                String startTime = editTextStartTime.getText().toString();
                String endTime = editTextEndTime.getText().toString();
                String className = editTextClassName.getText().toString();
                String applyReason = editTextApplyReason.getText().toString();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("startTime", startTime);
                    obj.put("endTime", endTime);
                    obj.put("className", className);
                    obj.put("applyReason", applyReason);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StringEntity entity = null;
                try {
                    entity = new StringEntity(obj.toString());
                    entity.setContentType("application/json");
                    entity.setContentEncoding("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return entity;
            }

        });

        return rootView;
    }

    private void init(View rootView) {
         editTextStartTime = (EditText) rootView.findViewById(R.id.editText_startTime);
         editTextEndTime = (EditText) rootView.findViewById(R.id.editText_endTime);
         editTextClassName = (EditText) rootView.findViewById(R.id.editText_className);
         editTextApplyReason = (EditText) rootView.findViewById(R.id.editText_applyReason);
         buttonCommit = (Button) rootView.findViewById(R.id.button_commit);
    }

}
