package com.example.monsheeppractica;

import static com.example.monsheeppractica.WebService.wsDataDownload.NombreTablas;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityLogin;
import com.example.monsheeppractica.WebService.wsDataDownload;
import com.example.monsheeppractica.sqlite.sqlite;

public class MainActivitySplash extends AppCompatActivity {
    ImageView logo;
    private ObjectAnimator animatorx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {
            //  Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }

        logo = (ImageView) findViewById(R.id.logo);


        animacion2();
        logo.setOnClickListener(view -> {
           // Intent intent = new Intent(this, MainActivityLogin.class);
//            startActivity(intent);
        });

    }

    private void animacion2() {
//        animatorx = ObjectAnimator.ofFloat(logo, "rotation", 0f, 360f);
//        //animatorx.setDuration(2000);
//       // animatorx.setRepeatCount(1);
//        animatorx.setRepeatCount(ObjectAnimator.INFINITE);
//        ObjectAnimator animX = ObjectAnimator.ofFloat(logo, "x", 50f);
//        ObjectAnimator animY = ObjectAnimator.ofFloat(logo, "y", 50f);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(animatorx);//,animX,animY);
//        animatorSet.start();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ivgirar);
        animation.setDuration(1500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(), MainActivityLogin.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logo.startAnimation(animation);



    }

    private void splash() {
        Intent intent = new Intent(getApplicationContext(), MainActivityLogin.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        new Handler().postDelayed(() -> {
//
//        }, 5000);
    }
}