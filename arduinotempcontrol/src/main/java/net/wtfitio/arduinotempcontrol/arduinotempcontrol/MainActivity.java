package net.wtfitio.arduinotempcontrol.arduinotempcontrol;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.FirstLoginSet;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments.ToolsListFragment;

public class MainActivity extends ActionBarActivity implements ToolsListFragment.onItemClick,FirstLoginSet.onContinueClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
          ToolsListFragment toolsfragment=ToolsListFragment.getinstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FirstLoginSet())
                    .commit();
        }
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