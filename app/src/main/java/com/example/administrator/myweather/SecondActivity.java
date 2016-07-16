package com.example.administrator.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.myweather.Utility.HttpCallBackListener;
import com.example.administrator.myweather.Utility.HttpUtil;
import com.example.administrator.myweather.db.CoolWeatherDB;
import com.example.administrator.myweather.db.SdUtils;
import com.example.administrator.myweather.model.Countryweather;

public class SecondActivity extends AppCompatActivity {
        private CoolWeatherDB coolWeatherDB;
        private Countryweather countryweather=new Countryweather();
        private   TextView tv;
    final String address="http://apis.baidu.com/apistore/weatherservice/cityname?cityname=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        final String getprovince = intent.getStringExtra("extra_data");
         tv= (TextView) findViewById(R.id.text);

       // tv.setText(getprovince);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        HttpUtil.sendHttpRequest(address, getprovince, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {

                countryweather=HttpUtil.handleWeatherResponse(response);
                coolWeatherDB.saveWeather(countryweather);
                boolean x= SdUtils.pathSaveSD("/data/data/com.example.administrator.myweather/databases/cool_weather", "shortweather.db", "xyq");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(countryweather.getDate());
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                  countryweather=coolWeatherDB.show(getprovince);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(countryweather.getDate());
                    }
                });
            }
        });


    }
}
