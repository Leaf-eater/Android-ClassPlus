package com.yuhang.classplus;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 宇航 on 2016/10/4.
 *
 */

public class Test {
    @org.junit.Test
    public void test() {
        JSONObject object;
        String content = "{\"语文\":\"88\",\"数学\":\"78\",\"计算机\":\"99\"}";
        try {
            object = new JSONObject(content);
            System.out.println(object.toString());
            if (object != null) {
                System.out.println("a");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
