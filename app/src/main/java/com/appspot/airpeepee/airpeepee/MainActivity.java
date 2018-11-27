package com.appspot.airpeepee.airpeepee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.appspot.airpeepee.airpeepee.model.db;
import com.google.android.gms.common.internal.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private  Thread thread;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;




String TAG;
    // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
    public boolean isOnline() {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            return isConnected;
        } catch (NetworkOnMainThreadException  e) {
            return false; }
    }

    db database ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button) findViewById(R.id.button1);
        b2=(Button) findViewById(R.id.button2);
        b3=(Button) findViewById(R.id.button3);
        b4=(Button) findViewById(R.id.button4);
        b5=(Button) findViewById(R.id.button5);
        b6=(Button) findViewById(R.id.button6);
        b7=(Button) findViewById(R.id.button7);
        b8= (Button) findViewById(R.id.button9);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        b5.setVisibility(View.GONE);
        b6.setVisibility(View.GONE);
        b7.setVisibility(View.GONE);
        b8.setVisibility(View.GONE);
        if(isOnline())
        {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            thread = new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 30;
                        // Update the progress bar and display the
                        //current value in the text view
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                                database = new db();
                               if(progressStatus > 100) {
                                   b1.setVisibility(View.VISIBLE);
                                   b2.setVisibility(View.VISIBLE);
                                   b3.setVisibility(View.VISIBLE);
                                   b4.setVisibility(View.VISIBLE);
                                   b5.setVisibility(View.VISIBLE);
                                   b6.setVisibility(View.VISIBLE);
                                   b7.setVisibility(View.VISIBLE);
                                   b8.setVisibility(View.VISIBLE);
                                   progressBar.setVisibility(View.GONE);
                                   thread.interrupt();
                               }
                            }
                        });
                        try {
                            // Sleep for 200 milliseconds.
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
           thread.start();


            // database = new db();
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inent = new Intent(v.getContext(),MapsActivity.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    startActivity(inent);
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent inent = new Intent(v.getContext(),LoginActivity.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    startActivity(inent);
                }
            });
            b3.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent inent = new Intent(v.getContext(),SearchActivity.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    startActivity(inent);
                }
            });
            b4.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent inent = new Intent(v.getContext(),DirectionActivity.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    startActivity(inent);
                }
            });
            b5.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent inent = new Intent(v.getContext(),AddActivity.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    startActivity(inent);
                }
            });
            b6.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent inent = new Intent(v.getContext(),MenuActivity.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    startActivity(inent);
                }
            });
            b7.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent inent = new Intent(v.getContext(),VideoActivity.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    startActivity(inent);
                }
            });
            b8.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent inent = new Intent(v.getContext(),EditProfileActivity.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    startActivity(inent);
                }
            });
        }
        else
        {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                Log.d(this.TAG, "Show Dialog: " + e.getMessage());
            }
        }












    }
}
