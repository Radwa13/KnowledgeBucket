package com.example.radwa.knowledgebucket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Welcome extends AppCompatActivity {
    @BindView(R.id.play)
    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.play)
    void play() {
        Intent intent = new Intent(this, Categories.class);
        startActivity(intent);
    }
}

