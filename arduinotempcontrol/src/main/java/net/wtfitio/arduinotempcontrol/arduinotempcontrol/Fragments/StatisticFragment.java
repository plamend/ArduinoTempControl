package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.feedObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.inputObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.R;

/**
 * Created by plamend on 2/27/14.
 */
public class StatisticFragment extends android.support.v4.app.Fragment {
    private transferInformation transferinformation;
    private static String FEED_ID = "feedid";
    private static String SET_MAX_VALUE = "set_max_value";
    private static String SET_RELAY = "set_relay";
    private static String SET_MAXFEED="set_maxfeed";
    public static StatisticFragment getInstance(int temp_feed_id, inputObject temp_set_relay, inputObject temp_set_max_value, feedObject temp_set_maxfeed){
        StatisticFragment fragment = new StatisticFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FEED_ID, temp_feed_id);
        bundle.putSerializable(SET_RELAY, temp_set_relay);
        bundle.putSerializable(SET_MAXFEED,temp_set_maxfeed);
        bundle.putSerializable(SET_MAX_VALUE,temp_set_max_value);
        fragment.setArguments(bundle);

        return fragment;
    }

    public interface transferInformation{
        void StatisticsRelayOnOff();
        void StatisticsreferentValueSet();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof transferInformation){
          this.transferinformation = (transferInformation) activity;
        }
        else{
            throw new IllegalArgumentException("Activity must implement transferInformation");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String feed_id= String.valueOf(arguments.getInt(FEED_ID));
        inputObject set_relay = (inputObject) arguments.getSerializable(SET_RELAY);
        inputObject set_max_value = (inputObject) arguments.getSerializable(SET_MAX_VALUE);
        feedObject set_maxfeed = (feedObject) arguments.getSerializable(SET_MAXFEED);
        View view = inflater.inflate(R.layout.fragment_statistic,container,false);
        RelativeLayout stat_editable = (RelativeLayout) view.findViewById(R.id.stat_editable);
        WebView web_view = (WebView)view.findViewById(R.id.stat_web_view);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.loadUrl("http://cms.wtfitio.net/emoncms/vis/rawdata&feedid="+feed_id+"&fill=0&units=C&embed=1");
        if(set_max_value==null&&set_relay==null){
            stat_editable.setVisibility(View.GONE);
        }

        return view;
    }
}
