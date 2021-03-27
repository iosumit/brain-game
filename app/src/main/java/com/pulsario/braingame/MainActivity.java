package com.pulsario.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button buttonSetting;
    Button play;
    Button _Custom_Play;
    SharedPreferences _Player_Pref;
    TextView _high_score;
    Button _Share;

    public static final String MY_PREF = "PLAYER_PREF";
    public static final String HIGH_SCORE = "HIGH_SCORE";
    public static final String MUSIC = "MUSIC";
    public static final String SOUND = "SOUND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSetting = findViewById(R.id.button_setting);
        play = findViewById(R.id.button_play);
        _high_score = findViewById(R.id.text_highscore);
        _Custom_Play = findViewById(R.id.button_choice_play);

        _Share  = findViewById(R.id.button_share);


        _Player_Pref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);

        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
        _Custom_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomPlayActivity.class);
                startActivity(intent);
            }
        });

        _high_score.setText(_Player_Pref.getInt(HIGH_SCORE, 0) + "");

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        _high_score.setText(sh.getInt(HIGH_SCORE, 0) + "");
    }
}