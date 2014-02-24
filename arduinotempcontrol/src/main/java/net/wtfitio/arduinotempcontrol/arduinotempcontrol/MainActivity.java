package net.wtfitio.arduinotempcontrol.arduinotempcontrol;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Adapters.inputListAdapter;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.feedObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.inputObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.FirstLoginSet;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.ToolsListFragment;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.Implemet.ServerInterfaceCom;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.ServerInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements ToolsListFragment.onItemClick,FirstLoginSet.onContinueClickListener {
    private ServerInterface http;
    List<inputObject>inputList ;
    List<feedObject> feedsList;
    inputListAdapter inputadapter;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inputList=new ArrayList<inputObject>();
        this.feedsList = new ArrayList<feedObject>();
        this.preferences = this.getSharedPreferences("Setings",MODE_PRIVATE);
        String server_tmp = preferences.getString("server","");
        String apikey_temp = preferences.getString("apikey","");

        if(server_tmp.equals("")&&apikey_temp.equals("")){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FirstLoginSet())
                    .commit();
        }
        else{
            httprequestfeedslist();
        }


        if (savedInstanceState == null) {


        }
    }
    private void httprequestinputlist(){
        getServer();


        ServerInterfaceCom http = new ServerInterfaceCom(getServer(), "input", null, null, null, getApiKey());
        http.getInputList(new ServerInterface.InputListcallback() {
            @Override
            public void onSuccess(List<inputObject> input) {
                addToVarInput(input);
                Log.v("","");
                CallMainScree(feedsList);

            }

            @Override
            public void onFailure(String message, Throwable cause) {

            }

        });


    }

    private String getServer() {
        String server;

       server= preferences.getString("server","http://cms.wtfitio.net/emoncms/");
        return server;
    }

    private String getApiKey() {
        String apikey;

        apikey= preferences.getString("apikey","");
        return apikey;
    }

    private void httprequestfeedslist() {
        this.http = new ServerInterfaceCom(getServer(),"feed",null,null,null,getApiKey());
        this.http.getFeedsList(new ServerInterface.FeedsListcallback() {
            @Override
            public void onSuccess(List<feedObject> feed) {
                addToVarFeed(feed);
                httprequestinputlist();
            }

            @Override
            public void onFailure(String message, Throwable cause) {

            }
        });
    }
    private void httprecuest(String feedid) {
        this.http = new ServerInterfaceCom(getServer(),"feed",feedid,null,null,getApiKey());
        this.http.getFeedValue(new ServerInterface.FeedValumecallback() {
            @Override
            public void onSuccess(String value) {
                Log.v("outputjjjjjjjj", value);
            }

            @Override
            public void onFailure(String message, Throwable cause) {
                Log.v("outputjjjjjjjj", "erroro");
            }
        });
    }
    private void addToVarFeed(List<feedObject> feed) {
        feedsList=feed;

    }


    private void addToVarInput(List<inputObject> input) {

     inputList=input;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void toolsFragmentItemSelected(int position) {

    }


    @Override
    public void firstLoginSetonContinueClicked(String server, String api) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("server",server);
        editor.putString("apikey",api);
        editor.commit();

       // httprecuest("7");

        httprequestfeedslist();






    }

    private void CallMainScree(List<feedObject> feedsList) {
        ToolsListFragment toolsfragment=ToolsListFragment.getinstance(feedsList);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,toolsfragment).commit();
        Log.v("done", "main screen Srated");
    }
}

