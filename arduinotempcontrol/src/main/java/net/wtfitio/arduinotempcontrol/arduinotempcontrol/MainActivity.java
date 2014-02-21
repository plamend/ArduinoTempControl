package net.wtfitio.arduinotempcontrol.arduinotempcontrol;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.FirstLoginSet;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.ToolsListFragment;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.Implemet.ServerInterfaceCom;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Service.ServerInterface;

public class MainActivity extends ActionBarActivity implements ToolsListFragment.onItemClick,FirstLoginSet.onContinueClickListener {
    private ServerInterface http;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
          ToolsListFragment toolsfragment=ToolsListFragment.getinstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FirstLoginSet())
                    .commit();
            httprecuest();
        }
    }

    private void httprecuest() {
        this.http = new ServerInterfaceCom("http://cms.wtfitio.net/emoncms/","feed","7",null,null,null);
        this.http.getFeedValue(new ServerInterface.FeedValumecallback() {
            @Override
            public void onSuccess(String value) {
                Log.v("outputjjjjjjjj", value);
            }

            @Override
            public void onFailure(String message, Throwable cause) {

            }
        });
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
        ToolsListFragment toolsfragment=ToolsListFragment.getinstance();
       getSupportFragmentManager().beginTransaction().replace(R.id.container,toolsfragment).commit();
    }
}