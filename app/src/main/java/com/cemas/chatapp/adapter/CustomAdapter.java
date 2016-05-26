package com.cemas.chatapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.cemas.chatapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cemas on 13.05.2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private Context context;
    private int layoutResourceId;
    private List<String> messageList;

    public CustomAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.messageList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    public void add(String message) {
        if(message != null) {
            this.messageList.add(message);
        }
    }

    //bu adapter'deki messageList'in eleman sayısınca list'imize istediğimiz şekilde row view sağlıyor
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            //LayoutInflater burada bizim custom row view'ımzın implementasyonunu sağlıyor
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(layoutResourceId, parent, false);

        }
        if(messageList != null && row.findViewById(R.id.textViewMessage) != null) {
            TextView textViewMessage = (TextView) row.findViewById(R.id.textViewMessage);

            String message = messageList.get(position);
            if(textViewMessage != null) {
                textViewMessage.setText(message);
            }
        }

        return row;
    }
}
