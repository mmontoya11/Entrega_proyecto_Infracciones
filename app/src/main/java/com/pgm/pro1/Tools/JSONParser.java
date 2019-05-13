package com.pgm.pro1.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class JSONParser {

    static String result = "";
    static InputStream is = null;
    static JSONObject jObject = null;

    public JSONParser() {

    }


    //Se crea un metodo de tipo JSONObject
    public JSONObject realizarHttpRequest(String url, String metodo, ArrayList<NameValuePair> parametro) {
        try {
            System.out.println(url + " url " + metodo);
            if (metodo == "POST") {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(parametro));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                System.out.println(is.toString());
            } else if (metodo == "GET") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String parametros = URLEncodedUtils.format(parametro, "utf-8");
                url += "?" + parametros;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncodingExc", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e("IOException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.i("result", result);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage() + " ");
        }

        try {
            jObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("JSONException 1", e.getMessage());
        }

        return jObject;
    }

    public JSONObject SubirFile(String url,String metodo, MultipartEntity parametro) {
        try{
            System.out.println(url + " url " + metodo + " " + parametro);
            if(metodo == "POST"){
                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                HttpPost httppost = new HttpPost(url);

                httppost.setEntity(parametro);
                HttpResponse httpResponse = httpclient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                System.out.println(is.toString());
                httpclient.getConnectionManager().shutdown();
            }
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncodingExce", e.getMessage());
        }catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getMessage());
        }catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }catch (Exception e) {
            Log.e("Exception", e.getMessage() + " ek");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.i("result", result);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage()+" e");
        }

        try {
            jObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("JSONException 1", e.getMessage());
        }

        return jObject;
    }


}
