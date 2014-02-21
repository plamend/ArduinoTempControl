package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.Implemet;

import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.ServerInterface;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    private AsyncHttpClient client;
    public ServerInterfaceCom(String url,String action,String feedid,String inputName,String inputValue,String api){
        this.BASE_URL=url;
        this.action = action;
        this.feedid=feedid;
        this.inputName=inputName;
        this.inputValue=inputValue;
        this.BASE_API=api;
        this.client=new AsyncHttpClient();
        this.client.setTimeout(1000);
        this.client.setMaxRetriesAndTimeout(1,200);
    }

    @Override
    public void getInputList(InputListcallback callback) {

        String url = buildURL(BASE_URL,action,feedid,inputName,inputValue,BASE_API);
        this.client.get(url,null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });


    }






    private String buildURL(String base_url, String action, String feedid, String inputName, String inputValue, String base_api) {
        Uri.Builder ub = Uri.parse(base_url).buildUpon();
        if (action.equals("feed")){
            ub.appendPath(action);
            if(feedid==null){
                ub.appendPath("list.json");
            }
            else{
                ub.appendPath("value.json?id="+feedid);
            }
        }
        if(action.equals("input")){
            ub.appendPath(action);
            if(inputName==null){
                ub.appendPath("list.json");
            }
            else{
                try {
                    ub.appendPath("post.json?json="+ URLEncoder.encode("{" + inputName + ":" + inputValue + "}", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        ub.appendPath("&apikey="+base_api);
        String url = ub.build().toString();
        return url;
    }

    @Override
    public void getFeedsList(FeedsListcallback callback) {

    }

    @Override
    public void getFeedValue(final FeedValumecallback callback) {
        String url = buildURL(BASE_URL,action,feedid,inputName,inputValue,BASE_API);
        this.client.get(url,null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               String out = new String(responseBody);
                callback.onSuccess(out);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });

    }
}
