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

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.R;

/**
 * Created by plamend on 2/27/14.
 */
public class StatisticFragment extends android.support.v4.app.Fragment {
    private transferInformation transferinformation;
    private static String FEED_ID = "feedid";
    public static StatisticFragment getInstance(int temp_feed_id){
        StatisticFragment fragment = new StatisticFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FEED_ID, temp_feed_id);
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
        View view = inflater.inflate(R.layout.fragment_statistic,container,false);
        WebView web_view = (WebView)view.findViewById(R.id.stat_web_view);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.loadUrl("http://cms.wtfitio.net/emoncms/vis/rawdata&feedid="+feed_id+"&fill=0&units=C&embed=1");


        return view;
    }
}
