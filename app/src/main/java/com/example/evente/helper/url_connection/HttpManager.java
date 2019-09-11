package com.example.evente.helper.url_connection;


import android.util.Log;

import com.example.evente.helper.MyGson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpManager {

    public static <T> MojApiRezultat<T> get(String url, Class<T> outputType) {
        HttpGet httpGet = new HttpGet(url);

        DefaultHttpClient client = new DefaultHttpClient();
        final MojApiRezultat<T> rezultat = new MojApiRezultat<>();

        try {
            HttpResponse response = client.execute(httpGet);

            InputStream inputStream = response.getEntity().getContent();

            String strJson = convertStreamToString(inputStream);

            T x = MyGson.build().fromJson(strJson, outputType);

            rezultat.isError=false;
            rezultat.value=x;
        } catch (IOException e) {
            Log.e("HttpManager",e.getMessage());
            rezultat.isError=true;
            rezultat.value=null;
            rezultat.errorMessage=e.getMessage();
        }
        return rezultat;
    }

public static String convertStreamToString(InputStream inputStream) throws IOException {
    String newLine = System.getProperty("line.separator");
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder result = new StringBuilder();
    boolean flag = false;
    for (String line; (line = reader.readLine()) != null; ) {
        result.append(flag ? newLine : "").append(line);
        flag = true;
    }
    return result.toString();
}
}
