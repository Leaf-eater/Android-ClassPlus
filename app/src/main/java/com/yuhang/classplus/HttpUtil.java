package com.yuhang.classplus;

import android.support.annotation.Nullable;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by 宇航 on 2016/10/3.
 *
 */

public class HttpUtil {

    private static AsyncHttpClient client = new AsyncHttpClient(); // 实例化对象

    static {
        client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
    }

    // 用一个完整url获取一个string对象
    public static void get(String urlString, AsyncHttpResponseHandler res)
    {
        client.get(urlString, res);
    }

    // url里面带参数
    public static void get(String urlString, RequestParams params,
                           AsyncHttpResponseHandler res){
        client.get(urlString, params, res);
    }

    // 不带参数，获取json对象或者数组
    public static void get(String urlString, JsonHttpResponseHandler res) {
        client.get(urlString, res);
    }


    // 带参数，获取json对象或者数组
    public static void get(String urlString, RequestParams params,
                           JsonHttpResponseHandler res) {
        client.get(urlString, params, res);
    }

    // 下载数据使用，会返回byte数据
    public static void get(String uString, BinaryHttpResponseHandler bHandler) {
        client.get(uString, bHandler);

    }

    public static AsyncHttpClient getClient(){
        return client;
    }

    public static void post(String url, RequestParams params, ResponseHandlerInterface responseHandler){
        client.post(url,params,responseHandler);
    }

    public static void post(android.content.Context context, java.lang.String url, @Nullable org.apache.http.HttpEntity entity, java.lang.String contentType, com.loopj.android.http.ResponseHandlerInterface responseHandler){
        client.post(context, url, entity, contentType, responseHandler);
    }

}
