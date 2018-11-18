package com.appspot.airpeepee.airpeepee;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.appspot.airpeepee.airpeepee.model.db;


public class MainActivity extends AppCompatActivity {


    db database ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1=(Button) findViewById(R.id.button1);
        Button b2=(Button) findViewById(R.id.button2);
        Button b3=(Button) findViewById(R.id.button3);
        Button b4=(Button) findViewById(R.id.button4);
        Button b5=(Button) findViewById(R.id.button5);
        Button b6=(Button) findViewById(R.id.button6);
        Button b7=(Button) findViewById(R.id.button7);
        database = new db();
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







    }
    }
