package com.pulsario.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.pulsario.braingame.Idea.CustomLogic;

import static com.pulsario.braingame.CustomPlayActivity.PLAY;
import static com.pulsario.braingame.MainActivity.MUSIC;
import static com.pulsario.braingame.MainActivity.MY_PREF;
import static com.pulsario.braingame.MainActivity.SOUND;

public class CustomStartActivity extends AppCompatActivity {

    CustomLogic logic;

    TextView timer;
    TextView health;
    TextView _Text_High_Score;
    ProgressBar progressBar;
    Button _oprand_1;
    Button _oprand_2;
    Button _operator;
    Button _Ans_1;
    Button _Ans_2;
    Button _Ans_3;
    Button _Ans_4;

    MediaPlayer M_music;
    MediaPlayer M_failed;
    MediaPlayer M_heart;

    LottieAnimationView Anim_Coin;
    LottieAnimationView Anim_Heart;

    CountDownTimer countDownTimer;
    SharedPreferences sharedPreferences;

    int y = 100;
    int _CorrectAnswer = 1;
    int _Health = 5;
    long _Timer = 10000;
    long _Time_Left = 0;
    int _Score = 0;
    int questions = 0;
    int _question_offset = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_start);

        logic = new CustomLogic(getIntent().getIntExtra(PLAY, 1));

        timer = findViewById(R.id.text_timer);
        progressBar = findViewById(R.id.progerss_timer);
        health = findViewById(R.id.text_health);
        _Text_High_Score = findViewById(R.id.text_high_score);

        _operator = findViewById(R.id.text_operator);
        _oprand_1 = findViewById(R.id.text_operand_first);
        _oprand_2 = findViewById(R.id.text_operand_second);

        _Ans_1 = findViewById(R.id.button_play_ans_1);
        _Ans_2 = findViewById(R.id.button_play_ans_2);
        _Ans_3 = findViewById(R.id.button_play_ans_3);
        _Ans_4 = findViewById(R.id.button_play_ans_4);



        Anim_Coin = findViewById(R.id.anim_coin);
        Anim_Heart = findViewById(R.id.anim_heart);

        M_music = MediaPlayer.create(this, R.raw.music);
        M_failed = MediaPlayer.create(this, R.raw.failed);
        M_heart = MediaPlayer.create(this, R.raw.whoop);

        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);

        M_music.setLooping(true);
        if (sharedPreferences.getBoolean(MUSIC, false)){
            M_music.setVolume(0,0);
        }
        if (sharedPreferences.getBoolean(SOUND, false)){
            M_heart.setVolume(0, 0);
            M_failed.setVolume(0, 0);
        }

        M_music.start();

        _Ans_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
                _Ans_1.setEnabled(false);
            }
        });
        _Ans_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
                _Ans_2.setEnabled(false);
            }
        });
        _Ans_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
                _Ans_3.setEnabled(false);
            }
        });
        _Ans_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(4);
                _Ans_4.setEnabled(false);
            }
        });

        delay();
    }
    void checkAnswer(int x){
        countDownTimer.cancel();

        questions++;
        if (questions%10==0){
            _question_offset++;
            logic.setEndRange(_question_offset*10);
        }

        if (x == _CorrectAnswer){

            Anim_Coin.cancelAnimation();
            Anim_Coin.playAnimation();

            _Score = _Score + (_question_offset * 10);

            _Text_High_Score.setText(_Score+"");

            delay();

        } else {
            _Health--;
            health.setText(_Health + "");

            if(_Health > 0){
                Anim_Heart.cancelAnimation();
                Anim_Heart.playAnimation();
                M_heart.start();
                delay();
            } else {
                M_music.stop();
                M_failed.start();

                Dialog d = new Dialog(this);
                d.setContentView(R.layout.dailog_get_health);
                Button exit = d.findViewById(R.id.button_exit);
                Button cont = d.findViewById(R.id.button_continue);
                TextView score = d.findViewById(R.id.text_score);
                score.setText(_Score+"");
                cont.setVisibility(View.GONE);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                d.setCancelable(false);
                d.setCanceledOnTouchOutside(false);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
            }
        }
    }

    void setRandomOperands(){
        _oprand_1.setText(logic.getFirstOperand()+"");
        _oprand_2.setText(logic.getSecondOperand()+"");
        switch (logic.getOperator()){
            case 1 : _operator.setText("+"); break;
            case 2 : _operator.setText("-"); break;
            case 3 : _operator.setText("*"); break;
            case 4 : _operator.setText("/"); break;
        }
        int[] x = logic.getAnswers();
        _Ans_1.setText(x[0]+"");
        _Ans_2.setText(x[1]+"");
        _Ans_3.setText(x[2]+"");
        _Ans_4.setText(x[3]+"");
        //Toast.makeText(this, logic.getAnswerPosition()+" ", Toast.LENGTH_SHORT).show();
        _Ans_1.setBackgroundResource(R.drawable.button_answer_wrong);
        _Ans_2.setBackgroundResource(R.drawable.button_answer_wrong);
        _Ans_3.setBackgroundResource(R.drawable.button_answer_wrong);
        _Ans_4.setBackgroundResource(R.drawable.button_answer_wrong);

        _CorrectAnswer = logic.getAnswerPosition();

        switch (_CorrectAnswer){
            case 1: _Ans_1.setBackgroundResource(R.drawable.button_answer_correct); break;
            case 2: _Ans_2.setBackgroundResource(R.drawable.button_answer_correct); break;
            case 3: _Ans_3.setBackgroundResource(R.drawable.button_answer_correct); break;
            case 4: _Ans_4.setBackgroundResource(R.drawable.button_answer_correct); break;
        }
        _Ans_1.setEnabled(true);
        _Ans_2.setEnabled(true);
        _Ans_3.setEnabled(true);
        _Ans_4.setEnabled(true);
    }

    void resetTimer(){

        countDownTimer = new CountDownTimer(_Time_Left, 200){
            @Override
            public void onTick(long millisUntilFinished) {
                y = (int)(millisUntilFinished * 0.01);
                timer.setText((millisUntilFinished / 1000) + "");
                progressBar.setProgress(y);
                _Time_Left = millisUntilFinished;
            }
            @Override
            public void onFinish() {
                progressBar.setProgress(5);
                checkAnswer(0);
            }
        }.start();
    }

    void delay(){
        progressBar.setProgress(0);
        countDownTimer = new CountDownTimer(500, 250){
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(0);
                //Log.e("Math ", "  2");
            }
            @Override
            public void onFinish() {
                _Time_Left = _Timer;
                setRandomOperands();
                resetTimer();
                progressBar.setProgress(100);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        M_music.pause();
        M_heart.start();
        countDownTimer.cancel();
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dailog_exit_menu);
        Button exit = d.findViewById(R.id.button_exit);
        Button cont = d.findViewById(R.id.button_continue);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                M_music.start();
                resetTimer();
            }
        });
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        M_music.stop();
        M_music.release();
        M_heart.release();
        M_failed.release();
        countDownTimer.cancel();
        M_music = null;
        M_failed = null;
        M_heart = null;
    }
}