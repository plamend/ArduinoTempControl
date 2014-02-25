package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Adapters.feedsAdapter;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.feedObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.R;

import java.util.List;

/**
 * Created by plamend on 2/17/14.
 */
public class ToolsListFragment extends Fragment {
    private onItemClick onItemClick;
    ListView list;
    static List<feedObject> feedlist;

   public interface onItemClick {
        void toolsFragmentItemSelected(int position);
    }

    public static ToolsListFragment getinstance(List<feedObject> feedsList){
        feedlist=feedsList;
      ToolsListFragment fragment = new ToolsListFragment();



        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onItemClick){
            this.onItemClick= (ToolsListFragment.onItemClick) activity;
        }
        else {
            throw new IllegalArgumentException("Activity must implement onItemClick");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        this.list = (ListView)view.findViewById(R.id.list);
        feedsAdapter adapter = new feedsAdapter(getActivity(),feedlist);
        this.list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClick.toolsFragmentItemSelected(position);
            }
        });
           Log.v("done", "Done");

        return view;
    }
}

