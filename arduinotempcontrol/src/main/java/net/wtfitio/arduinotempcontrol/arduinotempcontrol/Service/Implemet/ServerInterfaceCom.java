package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.Implemet;

import android.net.Uri;
import android.support.v7.appcompat.R;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.feedObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.inputObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.MainActivity;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.ServerInterface;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plamend on 2/20/14.
 */
public class ServerInterfaceCom implements ServerInterface {
    private String BASE_URL;
    private String BASE_API;
    private String action;
    private String feedid;
    private String inputName;
    private String inputValue;
    private HttpClient http;
    private AsyncHttpClient client;
    public ServerInterfaceCom(String url,String action,String feedid,String inputName,String inputValue,String api){
        this.BASE_URL=url;
        this.action = action;
        this.feedid=feedid;
        this.inputName=inputName;
        this.inputValue=inputValue;
        this.BASE_API=api;
        http = new DefaultHttpClient();
        this.client=new AsyncHttpClient();

        this.client.setMaxRetriesAndTimeout(2,1000);
    }

    @Override
    public void getInputList(final InputListcallback callback) {

        String url = buildURL(BASE_URL,action,feedid,inputName,inputValue,BASE_API);

         this.client.get(url,null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.v("inputlistttt",content);

                JSONArray inputarray = null;



                try {
                    inputarray = new JSONArray(content);
                    List<inputObject> listInput = parceInputList(inputarray);
                    Log.v("parce","0");
                    callback.onSuccess(listInput);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                callback.onFailure(responseBody,error);
            }
        });


    }

    private List<inputObject> parceInputList(JSONArray inputarray) throws JSONException {
        List<inputObject> tempList = new ArrayList<inputObject>();

        for(int i=0;i<inputarray.length();i++){
            inputObject tempInputItem = new inputObject();
            JSONObject c = inputarray.getJSONObject(i);
            tempInputItem.setName((String) c.get("name"));
            tempInputItem.setDescription((String) c.get("description"));
           // Log.v("parce","0");
            tempList.add(tempInputItem);
        }

        return tempList;
    }


    private String buildURL(String base_url, String action, String feedid, String inputName, String inputValue, String base_api) {
        Uri.Builder ub = Uri.parse(base_url).buildUpon();
        if (action.equals("feed")){
            ub.appendEncodedPath(action);
            if(feedid==null){
                ub.appendEncodedPath("list.json");
            }
            else{
                ub.appendEncodedPath("value.json?id=" + feedid);
                //ub.appendPath("value.json?id="+feedid);
            }
        }
        if(action.equals("input")){
            ub.appendEncodedPath(action);
            if(inputName==null){
                ub.appendEncodedPath("list.json");
            }
            else{
                try {
                    ub.appendEncodedPath("post.json?json="+ URLEncoder.encode("{" + inputName + ":" + inputValue + "}", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
       ub.appendEncodedPath("&apikey=" + base_api);
        String url = ub.build().toString();
        return url;
    }

    @Override
    public void getFeedsList(final FeedsListcallback callback) {
        String url = buildURL(BASE_URL, action, feedid, inputName, inputValue, BASE_API);
        this.client.get(url,null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               String out = new String(responseBody);
                try {
                    JSONArray inputarray = new JSONArray(out);
                    List<feedObject> feedlist = parceFeeds(inputarray);
                    callback.onSuccess(feedlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                callback.onFailure(responseBody,error);
            }
        });
    }

    private List<feedObject> parceFeeds(JSONArray inputarray) throws JSONException {
        List<feedObject> tempList = new ArrayList<feedObject>();

        for(int i=0;i<inputarray.length();i++){
            feedObject tempFeedItem = new feedObject();
            JSONObject c = inputarray.getJSONObject(i);
            tempFeedItem.setName((String) c.get("name"));
            tempFeedItem.setId(Integer.valueOf((String) c.get("id")));
            tempFeedItem.setTag((String)c.get("tag"));
            tempFeedItem.setPub(c.getBoolean("public"));
            if(c.getBoolean("public")==true){
            tempList.add(tempFeedItem);
            }
        }
             Log.v("parce","0");



        return tempList;
    }

    @Override
    public void getFeedValue(final FeedValumecallback callback) {
        String url = buildURL(BASE_URL,action,feedid,inputName,inputValue,BASE_API);
        this.client.get(url,null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               String out = new String(responseBody);
                int out_count = out.length();
                out=out.substring(1,out_count-1);
                callback.onSuccess(out);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                callback.onFailure(responseBody,error);
            }
        });

    }

    @Override
    public void setInputValue(final inputValuecallback callback) {
        String url = buildURL(BASE_URL,action,feedid,inputName,inputValue,BASE_API);
        this.client.get(url,null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responce = new String(responseBody);
                callback.onSuccess(responce);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.onFailure(responseBody, error);
            }
        });
    }
}
