package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.R;

/**
 * Created by plamend on 2/17/14.
 */
public class ToolsListFragment extends Fragment {
    private onItemClick onItemClick;
    ListView list;
   public interface onItemClick {
        void toolsFragmentItemSelected(int position);
    }

    public static ToolsListFragment getinstance(){
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


        return view;
    }
}

