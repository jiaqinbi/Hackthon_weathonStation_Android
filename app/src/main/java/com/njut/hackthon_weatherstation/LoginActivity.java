package com.njut.hackthon_weatherstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);


        Button login_btn = (Button)findViewById(R.id.btn_register);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText)findViewById(R.id.et_user_name)).getText().toString();
                String userpwd = ((EditText)findViewById(R.id.et_psw)).getText().toString();
                Log.w("username",""+username);
                Log.w("userpwd",""+userpwd);
                if(username.equals("admin") && userpwd.equals("123456")){
//                    new AlertDialog.Builder(MainActivity.this).setTitle("请输入用户名!").setMessage()
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else if(username.equals("") && !userpwd.equals("")){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("提示!")
                            .setMessage("用户名不能为空！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                }else if(userpwd.equals("") && !username.equals("")){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("提示!")
                            .setMessage("密码不能为空！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                }else if(!username.equals("") && !userpwd.equals("")){
                    if(!username.equals("admin") || !userpwd.equals("123456")) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("提示!")
                                .setMessage("用户名或密码输入错误！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create().show();
                    }
                }else{
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("提示!")
                            .setMessage("请输入用户名和密码！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                }


            }
        });


    }




 /*   private void parseJSONWithGSON(String jsonData) {
        JsonRootBean app = new Gson().fromJson(jsonData, JsonRootBean.class);
        List<Datastreams> streams = app.getData().getDatastreams();
        List<Datapoints> temperate = streams.get(0).getDatapoints(); //温度
        //int count = app.getData().getCount();//获取数据的数量
        for (int i = 0; i < temperate.size(); i++) {

            String time = temperate.get(i).getAt();
            String value = temperate.get(i).getValue();
            Log.w("www","time="+time);
            Log.w("www","value="+value);
        }

    }*/

}

