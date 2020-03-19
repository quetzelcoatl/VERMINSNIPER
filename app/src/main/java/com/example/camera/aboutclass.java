package com.example.camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class aboutclass extends AppCompatActivity {

    Button credits;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        credits = findViewById(R.id.creds);
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent about1 = new Intent(getApplicationContext(),credits.class);
                startActivity(about1);
            }
        });
    }
}
