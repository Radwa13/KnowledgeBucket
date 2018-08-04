package com.example.radwa.knowledgebucket;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.radwa.knowledgebucket.model.Questions;
import com.example.radwa.knowledgebucket.model.Result;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Questins_Activity extends AppCompatActivity {
    @BindView(R.id.answerA)
    Button answerA;
    @BindView(R.id.answerB)
    Button answerB;
    @BindView(R.id.answerC)
    Button answerC;
    @BindView(R.id.answerD)
    Button answerD;
    @BindView(R.id.question)
    TextView questionTv;
    @BindView(R.id.timer)
    Chronometer mChronometer;
    @BindView(R.id.score)
    TextView scoreTV;
    @BindView(R.id.heart1)
    ImageView heart1;
    @BindView(R.id.heart2)
    ImageView heart2;
    @BindView(R.id.heart3)
    ImageView heart3;
    @BindView(R.id.adView)
    AdView mAdView;
    int btnId;
    Questions mQestions;
    int index;
    int score = 0;
    int attempts = 3;
    int high_score;
    boolean answer;
    ArrayList<Result> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questins__activty);
        ButterKnife.bind(this);
        index = 0;
        scoreTV.setText(String.valueOf(score));
        results = getIntent().getParcelableArrayListExtra("hhhh");
        attachDtaToUi();
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(getString(R.string.timer_key))) {
                mChronometer.setBase(savedInstanceState.getInt(getString(R.string.timer_key))); // set base time for a chronometer
                mChronometer.start();

            }
            if (savedInstanceState.containsKey(getString(R.string.btnKey)) && savedInstanceState.containsKey(getString(R.string.answer_key))) {
                Button button = findViewById(savedInstanceState.getInt(getString(R.string.btnKey)));
                boolean answer = savedInstanceState.getBoolean(R.string.answer_key;
                if (answer)
                    button.setBackgroundResource(R.drawable.right);
                else
                    button.setBackgroundResource(R.drawable.wrong);

            }
            if(savedInstanceState.containsKey(R.string))
        } else {
            mChronometer.setBase(SystemClock.elapsedRealtime()); // set base time for a chronometer
            mChronometer.start();
        }
        mChronometer
                .setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        if (chronometer.getText().toString().equalsIgnoreCase("00:60"))
                            finishGame();
                    }
                });

        MobileAds.initialize(this, "ca-app-pub-4242632129376359~2954185187");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @OnClick(R.id.answerA)
    public void clickA() {
        buttonClicked(R.id.answerA, answerA);

    }

    @OnClick(R.id.answerB)
    public void clickB() {
        buttonClicked(R.id.answerB, answerB);

    }

    @OnClick(R.id.answerC)
    public void clickC() {
        buttonClicked(R.id.answerC, answerC);
    }

    @OnClick(R.id.answerD)
    public void clickD() {
        buttonClicked(R.id.answerD, answerD);

    }

    private static class MyHandler extends Handler {
    }

    private final MyHandler mHandler = new MyHandler();

    public class MyRunnable implements Runnable {
        private final WeakReference<Activity> mActivity;

        public MyRunnable(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            attachDtaToUi();

        }
    }

    private void attachDtaToUi() {
        questionTv.setText((Html.fromHtml(results.get(index).getQuestion()).toString()));
        answerA.setText(Html.fromHtml(results.get(index).getAnswers().get(0)).toString());
        answerB.setText(Html.fromHtml(results.get(index).getAnswers().get(1)).toString());
        answerC.setText(Html.fromHtml(results.get(index).getAnswers().get(2)).toString());
        answerD.setText(Html.fromHtml(results.get(index).getAnswers().get(3)).toString());
        answerA.setTag(results.get(index).getAnswers().get(0));
        answerB.setTag(results.get(index).getAnswers().get(1));
        answerC.setTag(results.get(index).getAnswers().get(2));
        answerD.setTag(results.get(index).getAnswers().get(3));
        answerA.setClickable(true);
        answerB.setClickable(true);
        answerC.setClickable(true);
        answerD.setClickable(true);
        answerA.setBackgroundResource(R.drawable.answer);
        answerB.setBackgroundResource(R.drawable.answer);
        answerC.setBackgroundResource(R.drawable.answer);
        answerD.setBackgroundResource(R.drawable.answer);


    }

    private void buttonClicked(int id, Button button) {

        answerA.setClickable(false);
        answerB.setClickable(false);
        answerC.setClickable(false);
        answerD.setClickable(false);

        btnId = id;


        if (button.getText().equals(results.get(index).getCorrectAnswer())) {
            setRight();
            button.setBackgroundResource(R.drawable.right);


            if (index < results.size()) {
                showNext();
            } else {
                finishGame();
            }


        } else {
            setAtemps();
            attempts--;
            button.setBackgroundResource(R.drawable.wrong);
            setWrong();
        }
    }

    private void setRight() {
        answer = true;
        score += 10;
        scoreTV.setText(String.valueOf(score));
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.right);
        mp.start();

    }

    private void setWrong() {
        answer = false;
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
        mp.start();
    }

    public void setAtemps() {
        if (attempts == 3) {
            heart3.setVisibility(View.INVISIBLE);

            if (index < results.size()) {
                index++;
            }

            showNext();

        } else if (attempts == 2) {
            heart2.setVisibility(View.INVISIBLE);

            if (index < results.size()) {
                index++;
            }

            showNext();


        } else if (attempts == 1) {
            heart1.setVisibility(View.INVISIBLE);

            finishGame();
        }

    }

    private void showNext() {

        MyRunnable mRunnable = new MyRunnable(this);
        mHandler.postDelayed(mRunnable, 2000);
    }

    public boolean checkHighScore(String category) {
        if (SharedPreferencesMethods.loadSavedPreferences(this, category) > 0) {
            if (score > SharedPreferencesMethods.loadSavedPreferences(this, category)) {
                high_score = score;
                SharedPreferencesMethods.savePreferences(this, category, score);
                return true;
            } else {
                high_score = SharedPreferencesMethods.loadSavedPreferences(this, category);
            }

        }
        if (SharedPreferencesMethods.loadSavedPreferences(this, category) == 0) {
            high_score = score;

            SharedPreferencesMethods.savePreferences(this, category, score);
            return true;
        }


        return false;
    }

    public void finishGame() {
        Intent intent = new Intent(Questins_Activity.this, GameOver.class);
        String category = results.get(0).getCategory();
        intent.putExtra(getString(R.string.category), results.get(0).getCategory());
        intent.putExtra(getString(R.string.score), score);
        if (checkHighScore(category)) {
            intent.putExtra(getString(R.string.new_high_score), "");
        }
        intent.putExtra(getString(R.string.high_sore), high_score);

        startActivity(intent);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.btnKey), btnId);
        int elapsedMillis = (int) (SystemClock.elapsedRealtime() - mChronometer.getBase());
        outState.putInt(getString(R.string.timer_key), elapsedMillis);
        outState.putInt(getString(R.string.timer_key), elapsedMillis);
        outState.putBoolean(getString(R.string.answer_key), answer);
        outState.putInt(getString(R.string.score_key), score);


    }
}

