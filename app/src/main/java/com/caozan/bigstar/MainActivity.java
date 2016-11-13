package com.caozan.bigstar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.caozan.bigstar.base.BaseActivity;
import com.caozan.bigstar.volley.IRequest;
import com.caozan.bigstar.volley.RequestListener;

public class MainActivity extends BaseActivity {
    private TextView tv;
    String url = "http://www.geeboo.com/library/cGetMyLibBookAction.go?email=692976@geeboo.cn&nowPage=1&password=88CD6G375J47A5BE9J9D&versionNumber=312&onePageCount=18&terminalType=1&accountId=1108763";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        tv = (TextView) findViewById(R.id.tv);
        IRequest.get(this, "http://www.geeboo.com/library/cGetMyLibBookAction.go?email=692976@geeboo.cn&nowPage=1&password=88CD6G375J47A5BE9J9D&versionNumber=312&onePageCount=18&terminalType=1&accountId=1108763", null, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, json, Toast.LENGTH_SHORT).show();
                Log.i("after", json);
                tv.setText(json);
            }
            @Override
            public void requestError(VolleyError e) {
                // TODO Auto-generated method stub
            }

        });
    }
}