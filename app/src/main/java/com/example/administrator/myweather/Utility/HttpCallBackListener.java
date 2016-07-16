package com.example.administrator.myweather.Utility;

/**
 * Created by Administrator on 2016/7/14.
 */
public interface HttpCallBackListener {
    void  onFinish(String response);
    void  onError(Exception e);
}
