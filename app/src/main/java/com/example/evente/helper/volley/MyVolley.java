package com.example.evente.helper.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.evente.MyApp;
import com.example.evente.helper.MyGson;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.Arrays;

public class MyVolley {
public static <T> void get (String urlStr, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener, BasicNameValuePair... inputParams)
{
    String urlParams = URLEncodedUtils.format(Arrays.asList(inputParams), "utf-8");
    String url = urlStr + "/" + urlParams;
    GsonRequest<T> gsonRequest = new GsonRequest<>(url, responseType, null, null, listener, errorListener, Request.Method.GET);
    MySingleton.getInstance(MyApp.getContext()).addToRequestQueue(gsonRequest);
}
    public static <T> void post (String urlStr, Class<T> responseType, final Response.Listener<T> listener, Response.ErrorListener errorListener, Object postObject)
    {
        String jsonStr = MyGson.build().toJson(postObject);
        GsonRequest<T> gsonRequest = new GsonRequest<>(urlStr, responseType, null, jsonStr, listener, errorListener, Request.Method.POST);
        MySingleton.getInstance(MyApp.getContext()).addToRequestQueue(gsonRequest);
    }

}
