package com.example.pushnotiffirebase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageList extends ArrayAdapter<Message> {
    private Activity context;
    //list of users
    List<Message> message;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    public MessageList(Activity context, List<Message> message) {
        super(context, R.layout.layout_message_item, message);
        this.context = context;
        this.message = message;
        // Get the database in firebase
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Message");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_message_item, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.title);
        TextView textviewemail = (TextView) listViewItem.findViewById(R.id.notify);
        TextView textviewnumber = (TextView) listViewItem.findViewById(R.id.sub);
        //getting user at position
        Message m = message.get(position);
        //set user name
        textViewName.setText(m.getTitle());
        //set user email
        textviewemail.setText(m.getNotifytext());
        //set user mobilenumber
        textviewnumber.setText(m.getNotifysubtext());
        return listViewItem;
    }
}
