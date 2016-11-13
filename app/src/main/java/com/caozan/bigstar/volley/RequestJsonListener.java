package com.caozan.bigstar.volley;

import com.android.volley.VolleyError;

/**
 * 小袁
 * Created by Administrator on 2015/3/11.
 */
public interface RequestJsonListener<T> {
    /**
     * 成功
     *
     */
    public void requestSuccess(T result);

    /**
     * 错误
     */
    public void requestError(VolleyError e);
}
