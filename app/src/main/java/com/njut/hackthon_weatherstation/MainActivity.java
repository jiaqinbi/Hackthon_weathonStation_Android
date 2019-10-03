package com.njut.hackthon_weatherstation;


import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends Activity {
    private static final String DeviceID = "529040114";
    private static final String ApiKey = "fp2tCoI51h=QjxkoEDrKhUcrTbI=";

    private static final String temperature = "temperature";//onenet平台上对应设备的其中一个数据流的名字
    private static final String humidity = "humidity";
    private static final String wind_speed = "wind_speed";
    private static final String wind_direction = "wind_direction";
    private static final String air_pressure = "air_pressure";
    private static final String wind_power = "wind_power";
    private static final String wind_angle = "wind_angle";

    private Handler handler = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                String responseData = (String)msg.obj;

                JsonRootBean app = new Gson().fromJson(responseData, JsonRootBean.class);
                List<Datastreams> streams = app.getData().getDatastreams();


                List<Datapoints> wind_angle_temp = streams.get(0).getDatapoints();
                String wind_angle_value = wind_angle_temp.get(0).getValue();   //风向角度    0 北  1,东北   2，东   3，东南  4，南   5，西南  6，西  7，西北

                List<Datapoints> temperature_temp = streams.get(1).getDatapoints();
                String temperature_value = temperature_temp.get(0).getValue();   //温度

                List<Datapoints> humidity_temp = streams.get(2).getDatapoints();
                String humidity_value = humidity_temp.get(0).getValue();    //湿度

                List<Datapoints> wind_speed_temp = streams.get(3).getDatapoints();
                String wind_speed_value = wind_speed_temp.get(0).getValue();   //风速

                List<Datapoints> wind_direction_temp = streams.get(4).getDatapoints();
                String wind_direction_value = wind_direction_temp.get(0).getValue();   //风向
                if(wind_direction_value.equals("0")){
                    wind_direction_value = "北风";
                }else if(wind_direction_value.equals("1")){
                    wind_direction_value = "东北风";
                }else if(wind_direction_value.equals("2")){
                    wind_direction_value = "东风";
                }else if(wind_direction_value.equals("3")){
                    wind_direction_value = "东南风";
                }else if(wind_direction_value.equals("4")){
                    wind_direction_value = "南风";
                }else if(wind_direction_value.equals("5")){
                    wind_direction_value = "西南风";
                }else if(wind_direction_value.equals("6")){
                    wind_direction_value = "西风";
                }else if(wind_direction_value.equals("7")){
                    wind_direction_value = "西北风";
                }

                List<Datapoints> air_pressure_temp = streams.get(5).getDatapoints();
                String air_pressure_value = air_pressure_temp.get(0).getValue();   //气压

                List<Datapoints> wind_power_temp = streams.get(6).getDatapoints();
                String wind_power_value = wind_power_temp.get(0).getValue();   //风力



                TextView temperate_tv = findViewById(R.id.temperature);
                temperate_tv.setText(temperature_value);
                if(Integer.parseInt(temperature_value)<100){
                    temperate_tv.setTextColor(android.graphics.Color.parseColor("#00ff00"));

                }else{
                    temperate_tv.setTextColor(android.graphics.Color.parseColor("#CD2626"));
                }

                TextView shumditity_tv = findViewById(R.id.humidity);
                shumditity_tv.setText(humidity_value);
                shumditity_tv.setTextColor(android.graphics.Color.parseColor("#00ff00"));
                TextView wind_speed_tv = findViewById(R.id.wind_speed);
                wind_speed_tv.setText(wind_speed_value);
                wind_speed_tv.setTextColor(android.graphics.Color.parseColor("#00ff00"));

                TextView air_pressure_tv = findViewById(R.id.air_pressure);
                air_pressure_tv.setText(air_pressure_value);
                //if(Integer.parseInt(air_pressure_value)<500){
                    air_pressure_tv.setTextColor(android.graphics.Color.parseColor("#00ff00"));
                //}else{
                 //   air_pressure_tv.setTextColor(android.graphics.Color.parseColor("#CD2626"));
                //}
                TextView wind_power_tv = findViewById(R.id.wind_power);
                wind_power_tv.setText(wind_power_value);
                if(Integer.parseInt(wind_power_value)<2){

                    wind_power_tv.setTextColor(android.graphics.Color.parseColor("#00ff00"));
                }else if(Integer.parseInt(wind_power_value)>=2){
                    openMusic();
                    Toast toast= Toast.makeText(MainActivity.this, "请注意，风力达到报警值"+wind_power_value+"级",Toast.LENGTH_LONG);
                    toast.show();
                    wind_power_tv.setTextColor(android.graphics.Color.parseColor("#CD2626"));
                }else{
                   // wind_power_tv.setTextColor(android.graphics.Color.parseColor("#CD2626"));
                }
                TextView wind_angle_tv = findViewById(R.id.wind_angle);
                wind_angle_tv.setText(wind_angle_value);
                wind_angle_tv.setTextColor(android.graphics.Color.parseColor("#00ff00"));
                TextView wind_direction_tv = findViewById(R.id.wind_direction);
                wind_direction_tv.setText(wind_direction_value);
                wind_direction_tv.setTextColor(android.graphics.Color.parseColor("#00ff00"));


                Log.w("temperature", "" + temperature_value);
                Log.w("humidity", "" + humidity_value);
                Log.w("wind_speed", "" + wind_speed_value);
                Log.w("wind_direction", "" + wind_direction_value);
                Log.w("air_pressure", "" + air_pressure_value);
                Log.w("wind_power", "" + wind_power_value);
                Log.w("wind_angle", "" + wind_angle_value);

                Log.w("返回值", "" + responseData);
            }
        };

    }
    //打开音乐的方法
    public  void  openMusic(){
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
        rt.play();
    }


    private final  Timer timer = new Timer();

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://api.heclouds.com/devices/" + DeviceID + "/datapoints?datastream_id=" + temperature + "," + humidity + "," + wind_speed + "," + wind_direction
                    + "," + air_pressure + "," + wind_power + "," + wind_angle).header("api-key", ApiKey).build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }



            try {
                final   String responseData = response.body().string();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Message  message = new Message();
                        message.obj = responseData;
                        message.what =100;
                        handler.sendMessage(message);


                    }
                }).start();


            } catch (IOException e) {
                e.printStackTrace();
            }




//                    parseJSONWithGSON(responseData);


        }
    };

    @Override
    public void    onStart(){
        super.onStart();
        timer.schedule(timerTask,0,2000);
    }

    @Override
    public void    onStop(){
        super.onStop();
        timer.cancel();
    }


}

