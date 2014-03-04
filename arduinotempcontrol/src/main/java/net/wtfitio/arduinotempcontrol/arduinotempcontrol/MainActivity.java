package net.wtfitio.arduinotempcontrol.arduinotempcontrol;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Adapters.inputListAdapter;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.feedObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.inputObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.FirstLoginSet;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.SettingsFragment;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.StatisticFragment;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.ToolsListFragment;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.Implemet.ServerInterfaceCom;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.ServerInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements ToolsListFragment.onItemClick,FirstLoginSet.onContinueClickListener,SettingsFragment.OnSaveandReloadClick,StatisticFragment.transferInformation {
    private ServerInterface http;
    List<inputObject>inputList ;
    List<feedObject> feedsList;
    inputListAdapter inputadapter;
    SharedPreferences preferences;
    inputObject temp_set_max_value=null;
    inputObject temp_set_relay=null;
    feedObject  temp_get_maxfeed = null;
    String temp_feed_value_toshow0;
    String temp_feed_value_toshow1;
    int   temp_feed_id;
    boolean secon_value_toget=false;
    boolean getvalue_secondrun = false;

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
        ServerInterfaceCom http = new ServerInterfaceCom(getServer(), "input", null, null, null, getApiKey());
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Processing inputs...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
        http.getInputList(new ServerInterface.InputListcallback() {
            @Override
            public void onSuccess(List<inputObject> input) {
                addToVarInput(input);
                pd.dismiss();
                Log.v("","");
                CallMainScree(feedsList);

            }

            @Override
            public void onFailure(byte[] message, Throwable cause) {
                pd.dismiss();
                CheckErrorMEssage(message, cause);
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
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Processing feeds...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
        this.http.getFeedsList(new ServerInterface.FeedsListcallback() {
            @Override
            public void onSuccess(List<feedObject> feed) {
                addToVarFeed(feed);
                pd.dismiss();
                httprequestinputlist();

            }

            @Override
            public void onFailure(byte[] message, Throwable cause) {
                pd.dismiss();
                CheckErrorMEssage(message, cause);
            }
        });
    }

    private void CheckErrorMEssage(byte[] message, Throwable cause) {
        if (message!=null){
            String msg = new String(message);
            ShowErrorMEssage(msg,cause);
        }
        else{
            String msg = getString(R.string.no_internet);
            ShowErrorMEssage(msg,cause);
        }
    }

    private void ShowErrorMEssage(String msg, Throwable cause) {
        Toast.makeText(this, new StringBuilder().append(getString(R.string.connection_problem)).append(msg).append(" ").append(cause).toString(),Toast.LENGTH_LONG).show();
    }

    private void httprecuest(final feedObject feedid) {

        this.http = new ServerInterfaceCom(getServer(),"feed",String.valueOf(feedid.getId()),null,null,getApiKey());

        this.http.getFeedValue(new ServerInterface.FeedValumecallback() {
            @Override
            public void onSuccess(String value) {



                if(secon_value_toget){
                    getvalue_secondrun=true;
                    secon_value_toget=false;
                }
                if(!getvalue_secondrun){
                temp_feed_id = feedid.getId();
                String temp_feed_tag = feedid.getTag();
                for (inputObject input : inputList) {
                    if (input.getName().equals(temp_feed_tag)) {
                        temp_set_max_value = input;
                    }
                    if (input.getDescription().equals(temp_feed_tag)) {
                        temp_set_relay = input;
                    }

                }

                for (feedObject feed : feedsList) {
                    if (feed.getName().equals(feedid.getTag())) {
                        temp_get_maxfeed = feed;
                        secon_value_toget = true;


                    }
                }

                }
                    if(secon_value_toget){
                    temp_feed_value_toshow0 = value;
                    getvalue_secondrun=true;
                    httprecuest(temp_get_maxfeed);
                    }
                    else{
                     if(!getvalue_secondrun){
                    temp_feed_value_toshow0 = value;
                     }
                        else{
                         temp_feed_value_toshow1 = value;

                     }
                    StatisticFragment fragment = StatisticFragment.getInstance(temp_feed_id, temp_set_relay, temp_set_max_value, temp_get_maxfeed, temp_feed_value_toshow0,temp_feed_value_toshow1);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment).addToBackStack(null)
                            .commit();

                    }


            }

            @Override
            public void onFailure(byte[] message, Throwable cause) {
                CheckErrorMEssage(message, cause);
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
            CallSettings(getServer(),getApiKey());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CallSettings(String server, String apiKey) {
        SettingsFragment fragment = SettingsFragment.getInstance(server,apiKey);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null)
                .commit();
    }


    @Override
    public void toolsFragmentItemSelected(int position) {
        temp_set_relay=null;
        temp_set_max_value=null;
        temp_get_maxfeed =null;
        temp_feed_id=0;
        getvalue_secondrun=false;
        secon_value_toget=false;
        temp_feed_value_toshow0=null;
        temp_feed_value_toshow1=null;
        feedObject temp_feed = feedsList.get(position);

        httprecuest(temp_feed);
        Log.v("feedname",temp_feed.getName());
       /* StatisticFragment fragment = StatisticFragment.getInstance(temp_feed_id,temp_set_relay,temp_set_max_value,temp_get_maxfeed, temp_feed_value_toshow);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null)
                .commit();*/



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



    @Override
    public void SettingsSaveAndReloadClicked(String server, String api) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("server",server);
        editor.putString("apikey",api);
        editor.commit();
    }

    @Override
    public void StatisticsRelayOnOff() {

    }

    @Override
    public void StatisticsreferentValueSet() {

    }
}

