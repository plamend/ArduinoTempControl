package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.R;

/**
 * Created by plamend on 2/17/14.
 */
public class FirstLoginSet extends Fragment {
    private onContinueClickListener onContinueClickListener;
    public interface onContinueClickListener{
        public void firstLoginSetonContinueClicked(String server,String api);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onContinueClickListener){
            this.onContinueClickListener= (FirstLoginSet.onContinueClickListener) activity;
        }
        else {
            throw new IllegalArgumentException("Activity must implement onItemClick");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_first_login,container,false);
        final EditText server = (EditText)view.findViewById(R.id.set_server);
        final EditText api = (EditText)view.findViewById(R.id.set_api);
        Button contin = (Button) view.findViewById(R.id.button_continue);

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_server=server.getText().toString();
                String str_api = api.getText().toString();
                if(!str_server.equals("")&&!str_api.equals("")){
                onContinueClickListener.firstLoginSetonContinueClicked(str_server, str_api);
                }
                else{
                    ShowMessage((String) getText(R.string.first_set_error));
                }
            }
        });

        return view;
    }

    private void ShowMessage(String message) {
        Toast.makeText(getActivity().getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }
}
