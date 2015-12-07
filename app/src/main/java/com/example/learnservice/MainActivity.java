package com.example.learnservice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;



public class MainActivity extends Activity {

    /**
     *  MainActivity 透過 intent 啟動 TimerService,
     *  要把 totalSec 給傳過去.
     */

    private TextView textTimer;
    private TextView showURI;
    private Context mContext;                          //跳出toast訊息的參數
    private UIHandler mUIHandler;
    private PowerKeyObserver mPowerKeyObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        textTimer = (TextView) findViewById (R.id.text_timer);              //顯示時間倒數的textview
        mContext = this;

         //若為停止計時狀態，顯示時間
        if (TimerService.mState.equals(TimerService.State.Stopped)){ showTimerDialog();}

        //若時間為停止狀態，即啟動
        if (TimerService.getTimerState().equals(TimerService.State.Stopped))
        {
            Intent intent = new Intent(TimerService.ACTION_PLAY);
            intent.putExtra(TimerService.TAG_TOTAL_SECOND, TimerService.getRemainSeconds());
            startService(intent);
        }

        else
        {
            Intent intent = new Intent(TimerService.ACTION_STOP);
            startService(intent);
        }
    }



    public void setRemainTimeText(String text){   //設定textTimer這個textview所顯示的內容，UIHandler.java有call到
        textTimer.setText(text);
    }

    public void showMsg() {
        Intent intent = new Intent(MainActivity.this,CustomDialogActivity.class);
        startActivity(intent);
    }

        //更改時間之處
    private void showTimerDialog() {
        // TODO Auto-generated method stub
        String stringTimer = "00:00:10";
        int intHour = 0;
        int intMin = 0;
        int intSec = 10;
        textTimer.setText(stringTimer);
        int totalSec = intHour * 3600 + intMin * 60  + intSec;
        TimerService.setRemainSeconds(totalSec);

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        if(TimerService.getTimerState().equals(TimerService.State.Running)){
            textTimer.setText(TimerService.getStringRemainSeconds());

            mUIHandler = new UIHandler(MainActivity.this);
            TimerService.registerHandler(mUIHandler);
            TimerService.resetServiceThreadHandler();

        }else{
            mUIHandler = new UIHandler(MainActivity.this);
            TimerService.registerHandler(mUIHandler);
            textTimer.setText(TimerService.getStringRemainSeconds());
        }

    }
    private void init() {
        mPowerKeyObserver = new PowerKeyObserver(this);
        mPowerKeyObserver.setHomeKeyListener(new PowerKeyObserver.OnPowerKeyListener() {
            @Override
            public void onPowerKeyPressed() {
                System.out.println("----> 按下電源鍵");
            }
        });
        mPowerKeyObserver.startListen();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPowerKeyObserver.stopListen();
    }
}
