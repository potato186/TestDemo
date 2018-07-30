package com.example.pa.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class TestActivity extends AppCompatActivity {
    private EditText etVCode;
    private EditText etVGetcode;
    private String phone;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        etVCode = (EditText) findViewById(R.id.et_v_code_test);
        etVGetcode = (EditText) findViewById(R.id.et_v_getcode_test);
        findViewById(R.id.tv_test1_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = etVGetcode.getText().toString();

                //获取验证码
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(TestActivity.this,"号码不能为空",Toast.LENGTH_SHORT).show();

                Log.i("1234",phone.toString());
                SMSSDK.getVerificationCode("86",phone);
            }
        });
        findViewById(R.id.tv_test_vcode_valiable_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交验证码验证
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(TestActivity.this,"号码不能为空",Toast.LENGTH_SHORT).show();

                number = etVCode.getText().toString();

                if (TextUtils.isEmpty(number))
                    Toast.makeText(TestActivity.this,"号码不能为空",Toast.LENGTH_SHORT).show();

                Log.i("1234",phone+","+number);
                SMSSDK.submitVerificationCode("86",phone,number);
            }
        });



        EventHandler handler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TestActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TestActivity.this,"语音验证发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TestActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        Log.i("test","test");

                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    Log.i("1234",throwable.toString());
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TestActivity.this,des,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        SMSSDK.registerEventHandler(handler);

    }


//    public void onClick(View v) {
//        String phone = etVGetcode.getText().toString();
//        switch (v.getId()){
//            case R.id.et_v_getcode:
//                //获取验证码
//                if (TextUtils.isEmpty(phone))
//                    Toast.makeText(this,"号码不能为空",Toast.LENGTH_SHORT).show();
//
//                Log.i("1234","hhh");
//                SMSSDK.getVerificationCode("86",phone,null);
//                Log.i("1234","hhh");
//                //语音
////                SMSSDK.getVoiceVerifyCode("86",phone);
//                break;
//
//            case R.id.tv_test_vcode_valiable:
//                //提交验证码验证
//                if (TextUtils.isEmpty(phone))
//                    Toast.makeText(this,"号码不能为空",Toast.LENGTH_SHORT).show();
//
//                String number = etVCode.getText().toString();
//
//                if (TextUtils.isEmpty(number))
//                    Toast.makeText(this,"号码不能为空",Toast.LENGTH_SHORT).show();
//
//                Log.i("1234",phone+","+number);
//                SMSSDK.submitVerificationCode("86",phone,number);
//                break;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

