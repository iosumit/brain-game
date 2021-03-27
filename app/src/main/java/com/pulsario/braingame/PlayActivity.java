package com.pulsario.braingame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.pulsario.braingame.Idea.Logic;

import static com.pulsario.braingame.MainActivity.HIGH_SCORE;
import static com.pulsario.braingame.MainActivity.MUSIC;
import static com.pulsario.braingame.MainActivity.MY_PREF;
import static com.pulsario.braingame.MainActivity.SOUND;

public class PlayActivity extends AppCompatActivity {
    Logic logic;
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

    LottieAnimationView _Anim_HighScore;

    CountDownTimer countDownTimer;

    SharedPreferences sharedPreferences;

    private RewardedAd rewardedAd;

    int y = 100;
    int _CorrectAnswer = 1;
    int _Health = 5;
    long _Timer = 10000;
    long _Time_Left = 0;
    int _High_Score = 0;
    int _Score = 0;
    int questions = 0;
    int _question_offset = 1;
    boolean highScoreBlast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        logic = new Logic();

        timer = findViewById(R.id.text_timer);
        progressBar = findViewById(R.id.progerss_timer);
        health = findViewById(R.id.text_health);

        _operator = findViewById(R.id.text_operator);
        _oprand_1 = findViewById(R.id.text_operand_first);
        _oprand_2 = findViewById(R.id.text_operand_second);

        _Ans_1 = findViewById(R.id.button_play_ans_1);
        _Ans_2 = findViewById(R.id.button_play_ans_2);
        _Ans_3 = findViewById(R.id.button_play_ans_3);
        _Ans_4 = findViewById(R.id.button_play_ans_4);

        _Text_High_Score = findViewById(R.id.text_high_score);

        Anim_Coin = findViewById(R.id.anim_coin);
        Anim_Heart = findViewById(R.id.anim_heart);
        /*_Heart = (AnimationDrawable) Anim_Heart.getBackground();
        _Coin = (AnimationDrawable) Anim_Coin.getBackground();*/
        _Anim_HighScore = findViewById(R.id.anim_highscore);

        M_music = MediaPlayer.create(this, R.raw.music);
        M_failed = MediaPlayer.create(this, R.raw.failed);
        M_heart = MediaPlayer.create(this, R.raw.whoop);

        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        _High_Score = sharedPreferences.getInt(HIGH_SCORE, 0);

        M_music.setLooping(true);
        if (sharedPreferences.getBoolean(MUSIC, false)){
            M_music.setVolume(0,0);
        }
        if (sharedPreferences.getBoolean(SOUND, false)){
            M_heart.setVolume(0, 0);
            M_failed.setVolume(0, 0);
        }
        M_music.start();

        delay();

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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        rewardedAd = new RewardedAd(this, getString(R.string.reward));

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                //Toast.makeText(PlayActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                //Toast.makeText(PlayActivity.this, "Not Loaded" + errorCode, Toast.LENGTH_SHORT).show();
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

    }
    void checkAnswer(int x){
        questions++;
        if (questions%15==0){
            _question_offset++;
            logic.setEndRange(_question_offset*10);
        }

        countDownTimer.cancel();

        //Log.e("Check: ", _CorrectAnswer+" "+x);

        if (x == _CorrectAnswer){

            Anim_Coin.cancelAnimation();
            Anim_Coin.playAnimation();

            _Score = _Score + (_question_offset * 10);

            _Text_High_Score.setText(_Score+"");

            if (!highScoreBlast && (_High_Score < _Score) && (_High_Score != 0)){
                _Anim_HighScore.cancelAnimation();
                _Anim_HighScore.playAnimation();
                highScoreBlast = true;
            }
            delay();

        } else {
            _Health--;
            health.setText(_Health + "");

            if(_Health > 0){
                /*_Heart.stop();
                _Heart.selectDrawable(0);
                _Heart.start();*/
                Anim_Heart.cancelAnimation();
                Anim_Heart.playAnimation();
                M_heart.start();

                delay();
            } else {
                countDownTimer.cancel();
                M_music.pause();
                M_failed.start();

                final Dialog d = new Dialog(this);
                d.setContentView(R.layout.dailog_get_health);
                Button exit = d.findViewById(R.id.button_exit);
                final Button cont = d.findViewById(R.id.button_continue);
                final Button resume = d.findViewById(R.id.button_resume);
                TextView score = d.findViewById(R.id.text_score);
                score.setText(_Score+"");
                resume.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        _Health = 1;
                        health.setText(_Health + "");
                        delay();
                        M_music.start();
                    }
                });
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rewardedAd.isLoaded()) {
                            Activity activityContext = PlayActivity.this;
                            RewardedAdCallback adCallback = new RewardedAdCallback() {
                                @Override
                                public void onRewardedAdOpened() {
                                    // Ad opened.
                                }

                                @Override
                                public void onRewardedAdClosed() {
                                    // Ad closed.
                                    rewardedAd = createAndLoadRewardedAd();
                                }

                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem reward) {
                                    // User earned reward.
                                    resume.setVisibility(View.VISIBLE);
                                    cont.setVisibility(View.GONE);
                                }

                                @Override
                                public void onRewardedAdFailedToShow(int errorCode) {
                                    // Ad failed to display.
                                    Toast.makeText(PlayActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                                }
                            };
                            rewardedAd.show(activityContext, adCallback);
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                        }
                    }
                });
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
                //Log.e("Math ", y+"  1");
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
        if (_High_Score < _Score){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(HIGH_SCORE, _Score);
            editor.apply();
        }

        M_music.stop();
        M_music.release();
        M_heart.release();
        M_failed.release();
        countDownTimer.cancel();
        M_music = null;
        M_failed = null;
        M_heart = null;
    }

    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                getString(R.string.reward));
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }
}