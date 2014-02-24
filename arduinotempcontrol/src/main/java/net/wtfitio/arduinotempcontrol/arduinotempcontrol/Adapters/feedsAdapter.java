package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes.feedObject;
import net.wtfitio.arduinotempcontrol.arduinotempcontrol.R;

import java.util.List;

/**
 * Created by plamend on 2/24/14.
 */
public class feedsAdapter extends ArrayAdapter<feedObject> {
    public feedsAdapter(Context context, List objects) {
        super(context, R.layout.list_feeds, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = initView(parent);

        }
        feedItemsHolder holder = (feedItemsHolder) convertView.getTag();
        feedObject item =  getItem(position);
         String feed_name = item.getName();
         holder.feedName.setText(feed_name);
         String feed_id = String.valueOf(item.getId());
         holder.feedId.setText(feed_id);


        return convertView;
    }

    private View initView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_feeds,parent,false);
        feedItemsHolder holder = createholder(view);
        view.setTag(holder);
        return view;
    }

    private feedItemsHolder createholder(View view) {
        feedItemsHolder holder = new feedItemsHolder();
        holder.feedName=(TextView) view.findViewById(R.id.feed_name);
        holder.feedId = (TextView) view.findViewById(R.id.feed_id);
        return holder;
    }

    private class feedItemsHolder{
        private TextView feedName;
        private TextView feedId;
    }
}
