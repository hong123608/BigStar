package com.caozan.bigstar.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by caozan on 2016/11/2.
 */

public abstract class BaseActivity extends FragmentActivity {



    private BaseActivity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
    }

    //用来获取布局中TextView的方法
    public TextView tyByid(int id) {
        return (TextView) findViewById(id);
    }

    public EditText etById(int id) {
        return (EditText) findViewById(id);
    }

    //用来获取布局中Button的方法
    public Button butByid(int id) {
        return (Button) findViewById(id);
    }

    //用来获取布局中ImageView的方法
    public ImageView imgByid(int id) {
        return (ImageView) findViewById(id);
    }

    //用来获取布局中LinearLayout的方法
    public LinearLayout linByid(int linResIds) {
        return (LinearLayout) findViewById(linResIds);
    }

    //用来弹出Toast的方法  时间为5秒钟
    public void toastL(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    //用来弹出Toast的方法  时间为3秒钟


}
