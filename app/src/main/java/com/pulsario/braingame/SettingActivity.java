package com.pulsario.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import static com.pulsario.braingame.MainActivity.HIGH_SCORE;
import static com.pulsario.braingame.MainActivity.MUSIC;
import static com.pulsario.braingame.MainActivity.MY_PREF;
import static com.pulsario.braingame.MainActivity.SOUND;

public class SettingActivity extends AppCompatActivity {
    ToggleButton  _Music;
    ToggleButton  _Sound;
    Button _Back;
    Button _Share;
    Button _Like;
    SharedPreferences _Player_Pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView ver = findViewById(R.id.text_version);
        _Music = findViewById(R.id.toggle_music);
        _Sound = findViewById(R.id.toggle_sound);

        _Share  = findViewById(R.id.button_share);
        _Back = findViewById(R.id.button_back);
        _Like = findViewById(R.id.button_like);

        _Player_Pref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);

        _Music.setChecked(_Player_Pref.getBoolean(MUSIC, false));
        _Sound.setChecked(_Player_Pref.getBoolean(SOUND, false));

        try{
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version  = packageInfo.versionName;
            int versioCode = packageInfo.versionCode;
            //Log.e("TAG ", " Ver : "+ versioCode + " Version : "+ version);
            ver.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        _Music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = _Player_Pref.edit();
                editor.putBoolean(MUSIC, isChecked);
                editor.apply();
            }
        });
        _Sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = _Player_Pref.edit();
                editor.putBoolean(SOUND, isChecked);
                editor.apply();
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
                intent.putExtra(Intent.EXTRA_TEXT, "Interesting Game : Brain Game Math Test https://play.google.com/store/apps/details?id=com.pulsario.braingame");
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
    }
}