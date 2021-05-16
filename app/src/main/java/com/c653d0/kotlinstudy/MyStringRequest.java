package com.c653d0.kotlinstudy;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

public class MyStringRequest extends StringRequest {
    public MyStringRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
           parsed = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
