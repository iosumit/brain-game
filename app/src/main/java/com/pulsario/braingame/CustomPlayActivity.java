package com.pulsario.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomPlayActivity extends AppCompatActivity {
    Button buttonSetting;
    Button _Back;
    Button _Share;
    Button _Plus;
    Button _Minus;
    Button _Multiply;
    Button _Divide;
    Button _Like;

    public static final String PLAY = "PLAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_play);

        buttonSetting = findViewById(R.id.button_setting);
        _Share  = findViewById(R.id.button_share);
        _Back = findViewById(R.id.button_back);

        _Plus = findViewById(R.id.button_plus);
        _Minus = findViewById(R.id.button_minus);
        _Multiply = findViewById(R.id.button_multiply);
        _Divide = findViewById(R.id.button_divide);
        _Like = findViewById(R.id.button_like);


        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomPlayActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        _Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        _Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "Interesting Game : Brain Game Math IQ Test https://play.google.com/store/apps/details?id=com.pulsario.braingame");
                startActivity(intent);
            }
        });
        _Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.pulsario.braingame"));
                startActivity(i);
            }
        });

        _Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomPlayActivity.this, CustomStartActivity.class);
                intent.putExtra(PLAY, 1);
                startActivity(intent);
            }
        });
        _Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomPlayActivity.this, CustomStartActivity.class);
                intent.putExtra(PLAY, 2);
                startActivity(intent);
            }
        });
        _Multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomPlayActivity.this, CustomStartActivity.class);
                intent.putExtra(PLAY, 3);
                startActivity(intent);
            }
        });
        _Divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomPlayActivity.this, CustomStartActivity.class);
                intent.putExtra(PLAY, 4);
                startActivity(intent);
            }
        });
    }
}