package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    private static String CURRENT_VALUE="current_value";
    private static String VALUE_TO_SET = "value_to_set";
    EditText stat_max_value_edit;

    public static StatisticFragment getInstance(int temp_feed_id, inputObject temp_set_relay, inputObject temp_set_max_value, feedObject temp_set_maxfeed, String temp_feed_value_toshow0,String temp_feed_value_toshow1){
        StatisticFragment fragment = new StatisticFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FEED_ID, temp_feed_id);
        bundle.putSerializable(SET_RELAY, temp_set_relay);
        bundle.putSerializable(SET_MAXFEED, temp_set_maxfeed);
        bundle.putSerializable(SET_MAX_VALUE,temp_set_max_value);
        bundle.putString(CURRENT_VALUE, temp_feed_value_toshow0);
        bundle.putString(VALUE_TO_SET, temp_feed_value_toshow1);
        fragment.setArguments(bundle);


        return fragment;
    }

    public interface transferInformation{
        void StatisticsRelayOnOff(String inputName,String inputValue);
        void StatisticsreferentValueSet(String inputName,String inputValue);
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
        final inputObject set_relay = (inputObject) arguments.getSerializable(SET_RELAY);
        final inputObject set_max_value = (inputObject) arguments.getSerializable(SET_MAX_VALUE);
        feedObject set_maxfeed = (feedObject) arguments.getSerializable(SET_MAXFEED);
        View view = inflater.inflate(R.layout.fragment_statistic,container,false);
        RelativeLayout stat_editable = (RelativeLayout) view.findViewById(R.id.stat_editable);
        WebView web_view = (WebView)view.findViewById(R.id.stat_web_view);
        TextView stat_current_value=(TextView)view.findViewById(R.id.stat_current_value_view);
        this.stat_max_value_edit = (EditText) view.findViewById(R.id.stat_maxvalue_edit);
        Button set_max_value_button = (Button)view.findViewById(R.id.stat_save_maxvalue);
        final ToggleButton stat_onoff_button = (ToggleButton)view.findViewById(R.id.stat_onoff_button);
        stat_onoff_button.setTextOn("On");
        stat_onoff_button.setTextOff("Off");

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.loadUrl("http://cms.wtfitio.net/emoncms/vis/rawdata&feedid="+feed_id+"&fill=0&units=C&embed=1");
        stat_current_value.setText(arguments.getString(CURRENT_VALUE));
        stat_max_value_edit.setText(arguments.getString(VALUE_TO_SET));
        if(set_max_value==null&&set_relay==null){
            stat_editable.setVisibility(View.GONE);

            Log.v("test", "test");
        }

        set_max_value_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String temp_max_value=stat_max_value_edit.getText().toString();
               if(temp_max_value.equals("")){
                   ShowNewMessage(getString(R.string.value_empty));
               }
                else{
                   if(Integer.valueOf(temp_max_value)<0||Integer.valueOf(temp_max_value)>100){

                       ShowNewMessage(getString(R.string.between_0_100));

                   }
                   else{

                       referentvalueset(temp_max_value);
                   }
               }
            }

            private void referentvalueset(String temp_max_value) {
                assert set_max_value != null;

                transferinformation.StatisticsreferentValueSet(set_max_value.getName(),temp_max_value);
            }
        });

        stat_onoff_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stat_onoff_button.getText().toString().equals("On")){
                    assert set_relay != null;
                    transferinformation.StatisticsRelayOnOff(set_relay.getName(),"1");
                }
                if(stat_onoff_button.getText().toString().equals("Off")){
                    assert set_relay != null;
                    transferinformation.StatisticsRelayOnOff(set_relay.getName(),"0");
                }
            }
        });

        return view;
    }

    private void ShowNewMessage(String string) {
        Toast.makeText(getActivity(),string,Toast.LENGTH_SHORT).show();
    }
}
