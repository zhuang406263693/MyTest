package com.school.lenovo.okhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    private static final String TAG = "HttpUtil";
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    private String json ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                json = toJson("1","1");
                Log.i(TAG,json);
                RequestBody requestBody = RequestBody.create(JSON,json);
                Request request = new Request.Builder()
                        .url("http://api.weafung.com/index.php/Auth/login")
                        .method("Post",null)
                        .post(requestBody)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (!response.message().equals("OK")){
                        Log.i(TAG,"网络出错");
                    }else{
                        String result = new String(response.body().bytes());
                        Log.i(TAG,result);
                        if (!result.contains('"'+"error_code"+'"'+":0")){
                            Log.d(TAG,"密码出错");
                        }else{
                            Log.d(TAG,"解析数据");
                        }
                    }
                } catch (IOException e) {
                    Log.i(TAG,"网络出错");
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public String toJson(String username,String password){
//        char c = '"';
//        return "{"+c+"username"+c+":"+c+username+c+","+c+"password"+c+":"+c+password+c+"}";
        JsonObject object = new JsonObject();
        object.addProperty("username",username);
        object.addProperty("password",password);
        Log.i(TAG,object.toString());
        return object.toString();
    }
}
