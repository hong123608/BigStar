package com.caozan.bigstar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.caozan.bigstar.base.BaseActivity;
import com.caozan.bigstar.imageload.EasyImageLoader;
import com.caozan.bigstar.volley.IRequest;
import com.caozan.bigstar.volley.RequestListener;

public class MainActivity extends BaseActivity {
//zyh test
    private ImageView img;

    private TextView tv;
    String url = "http://www.geeboo.com/library/cGetMyLibBookAction.go?email=692976@geeboo.cn&nowPage=1&password=88CD6G375J47A5BE9J9D&versionNumber=312&onePageCount=18&terminalType=1&accountId=1108763";

    String urlImg = "http://musicdata.baidu.com/data2/pic/89781947/89781947.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        tv = (TextView) findViewById(R.id.tv);
        img = (ImageView) findViewById(R.id.img);

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
        EasyImageLoader.show(urlImg, img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   EasyImageLoader.show(urlImg, img);

            }
        });


    }
}