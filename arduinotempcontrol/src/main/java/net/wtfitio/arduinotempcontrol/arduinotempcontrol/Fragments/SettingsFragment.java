package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.R;

/**
 * Created by plamend on 2/25/14.
 */
public class SettingsFragment extends android.support.v4.app.Fragment {

    private OnSaveandReloadClick onSaveAndRelaodClick;
    private static String SERVER="server" ;
     private static String API="api";
   private String server;
    private  String api;
    private EditText server_view;
    private EditText api_view;

    public interface OnSaveandReloadClick{
        void SettingsSaveAndReloadClicked(String server,String api);
    }
    public static SettingsFragment getInstance(String Server,String api){
        SettingsFragment fragment = new SettingsFragment();
        Bundle bund = new Bundle();
        bund.putString(SERVER,Server);
        bund.putString(API,api);
        fragment.setArguments(bund);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnSaveandReloadClick){
            this.onSaveAndRelaodClick = (OnSaveandReloadClick) activity;
        }
        else{
            throw new IllegalArgumentException("Activity must implement onSaveAndRelaodClick");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_setiings,container,false);
        this.server_view = (EditText) view.findViewById(R.id.fragment_settings_server);
        this.api_view = (EditText) view.findViewById(R.id.fragment_settings_api);
        Button save_view = (Button)view.findViewById(R.id.fragment_settings_apply);
        this.server = bundle.getString(SERVER);
        this.api =bundle.getString(API);
        server_view.setText(server);
        api_view.setText(api);
        save_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_server = server_view.getText().toString();
                String str_api = api_view.getText().toString();
                if(!str_server.equals("")&&!str_api.equals("")){
                onSaveAndRelaodClick.SettingsSaveAndReloadClicked(str_server,str_api);
                }
                else{
                    ShowMessage((String) getText(R.string.first_set_error));
                }
            }
        });

        return view;
    }

    private void ShowMessage(String message) {
        Toast.makeText(getActivity().getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }
}
