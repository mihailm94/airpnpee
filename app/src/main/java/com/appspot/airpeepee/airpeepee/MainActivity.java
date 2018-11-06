package com.appspot.airpeepee.airpeepee;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.appspot.airpeepee.airpeepee.model.db;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b2=(Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent inent = new Intent(v.getContext(),LoginActivity.class);

                // calling an activity using <intent-filter> action name
                //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                startActivity(inent);
            }
        });


        db database = new db("29040668", "10555");



    }
    }
