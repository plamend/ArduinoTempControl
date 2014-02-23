package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.inputObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.MainActivity;

import java.util.List;

/**
 * Created by plamend on 2/23/14.
 */
public  class inputListAdapter extends ArrayAdapter{


    public inputListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);

    }



    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void add(Object object) {
        super.add(object);
    }
}
