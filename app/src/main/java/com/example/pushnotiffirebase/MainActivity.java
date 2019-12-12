package com.example.pushnotiffirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText editTitletxt, editNotifytexttxt, editNotifySubtexttxt;

    FirebaseDatabase database;
    DatabaseReference mDatabase;

    Button Create;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the database in firebase
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Message");

        // define the textview
        editTitletxt = findViewById(R.id.titleTxt);
        editNotifytexttxt = findViewById(R.id.notifytexttTxt);
        editNotifySubtexttxt = findViewById(R.id.notifySubtextTxt);

        Create = (Button)findViewById(R.id.notifyBtn);
        Button clear = (Button) findViewById(R.id.clearBtn);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTitletxt.setText("");
                editNotifytexttxt.setText("");
                editNotifySubtexttxt.setText("");
            }
        });

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Message message = new Message(editTitletxt.getText().toString(), editNotifytexttxt.getText().toString(), editNotifySubtexttxt.getText().toString());

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(message.getTitle()).exists())
                        {
                            Toast.makeText(MainActivity.this,"Message already exists!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            NotificationManager mNotificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                                        "YOUR_CHANNEL_NAME",
                                        NotificationManager.IMPORTANCE_DEFAULT);
                                channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
                                mNotificationManager.createNotificationChannel(channel);
                            }
                            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                                    .setContentTitle("Notification") // title for notification
                                    .setContentText("Berhasil memasukkan data!")// message for notification
                                    .setAutoCancel(true); // clear notification after click
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            mBuilder.setContentIntent(pi);
                            mNotificationManager.notify(0, mBuilder.build());

                            mDatabase.child(message.getTitle()).setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    nm.notify(0, mBuilder.build());

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
