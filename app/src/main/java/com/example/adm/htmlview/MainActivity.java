package com.example.adm.htmlview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TextView tv = (TextView)findViewById(R.id.tv);
            tv.setText((String)msg.obj);
        }
    };
    public void click(View v){
        final String path = "https://www.baidu.com";
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    conn.connect();
                    if(conn.getResponseCode()==200){
                        InputStream is = conn.getInputStream();
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        byte[] b = new byte[1024];
                        int len = 0;
                        while((len = is.read(b))!=-1){
                            os.write(b,0,len);
                        }
                        os.close();
                        String text = new String(os.toByteArray());
                        Message msg = new Message();
                        msg.obj = text;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
