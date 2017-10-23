package com.lizihao.mobileguard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private RelativeLayout mRlSplashRoot;
    private TextView mTvSplashTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initAnimation();
        checkVersion();
    }

    private void checkVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8080/version.json");
                    HttpURLConnection content = (HttpURLConnection) url.openConnection();
                    content.setConnectTimeout(5000);
                    content.setReadTimeout(5000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content.getInputStream()));
                    StringBuffer stringBuffer = new StringBuffer("");
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        stringBuffer.append(line);
                        line = bufferedReader.readLine();
                    }
                    Log.i(TAG, "run: " + stringBuffer);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initData() {
        mTvSplashTitle.setText("开发版");
    }

    private void initAnimation() {
        AlphaAnimation aa = new AlphaAnimation(0.0F, 1.0F);
        aa.setDuration(3000);
        //保持变换后的样式
        aa.setFillAfter(true);
        ScaleAnimation sa = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F);
        sa.setDuration(3000);
        sa.setFillAfter(true);
        RotateAnimation ra = new RotateAnimation(0F, 360F,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F);
        ra.setDuration(3000);
        ra.setFillAfter(true);
        AnimationSet as = new AnimationSet(false);
        as.addAnimation(sa);
        as.addAnimation(ra);
        as.addAnimation(aa);
        mRlSplashRoot.setAnimation(as);
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
        mRlSplashRoot = (RelativeLayout) findViewById(R.id.rl_splash_root);
        mTvSplashTitle = (TextView) findViewById(R.id.tv_splash_title);
    }
}
