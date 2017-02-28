package com.hp.mss.print.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hp.mss.print.R;
import com.hp.mss.print.helper.TAG;

public class OrderActivity extends AppCompatActivity {

    Button btnPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.sample_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnPrint = (Button) findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, SystemActivity.class);
                Bundle b = new Bundle();
                b.putString(TAG.TEXT_PRINT, "Ahihi");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}