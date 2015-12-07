package com.example.learnservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class PowerKeyObserver {
    private Context mContext;
    private IntentFilter mIntentFilter;
    public OnPowerKeyListener mOnPowerKeyListener;
    private PowerKeyBroadcastReceiver mPowerKeyBroadcastReceiver;


    public PowerKeyObserver(Context context) {
        this.mContext = context;
    }

    //注册廣播接收者
    public void startListen(){
        mIntentFilter=new IntentFilter(Intent.ACTION_SCREEN_ON);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mPowerKeyBroadcastReceiver=new PowerKeyBroadcastReceiver();
        mContext.registerReceiver(mPowerKeyBroadcastReceiver, mIntentFilter);
        System.out.println("----> 開始監聽");
    }

    //取消廣播接收者
    public void stopListen(){
        if (mPowerKeyBroadcastReceiver!=null) {
            mContext.unregisterReceiver(mPowerKeyBroadcastReceiver);
            System.out.println("----> 停止監聽");
        }
    }

    // 對外暴露接口
    public void setHomeKeyListener(OnPowerKeyListener powerKeyListener) {
        mOnPowerKeyListener = powerKeyListener;
    }

    // 回調接口
    public interface OnPowerKeyListener {
        public void onPowerKeyPressed();
    }

    //廣播接收者
    class PowerKeyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {      //讓倒數暫停
                mOnPowerKeyListener.onPowerKeyPressed();
                Intent i = new Intent();
                i.setClass(context, motherfucker.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                context.startActivity(i);
            }
            else  {  //讓倒數重新開始
                mOnPowerKeyListener.onPowerKeyPressed();

            }
        }
    }

    public class motherfucker extends Activity{
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_main);

            Intent intent123 = new Intent(TimerService.ACTION_STOP);
            startService(intent123);
        }
    }
}