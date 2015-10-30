package com.example.learnservice;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
//import android.view.Menu;
import android.view.View;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.MediaController;
import android.widget.VideoView;
import android.net.Uri;



public class MainActivity extends Activity {

    /**
     *  MainActivity 透過 intent 啟動 TimerService,
     *  要把 totalSec 給傳過去.
     *
     */

    private TextView textTimer;
    private TextView showURI;
    private Context mContext;                          //跳出toast訊息的參數
    private UIHandler mUIHandler;
    private NotificationManager myNotManager;


    final int NOTIFICATION_ID = 1;
    //Video
    private String strVideoPath = "";
    //private VideoView mVideoView01;
    private  VideoView Myvideo01;

    /* 預設判別是否安裝記憶卡flag為false */
    private boolean bIfSDExist = false;
    //Video

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myNotManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_main);
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
    /*public void showEnding(){
    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    View customDialog = inflater.inflate(R.layout.custom_dialog, null);
        //Toast.makeText(mContext,"https://www.youtube.com/watch?v=Q0jr7vRH7_s!!!",Toast.LENGTH_SHORT).show();

     new AlertDialog.Builder(MainActivity.this)
             .setTitle("時間到!!!")
             .setView(customDialog)
             .setPositiveButton("Come on!",
                     new DialogInterface.OnClickListener()
                     {
                         public void onClick(DialogInterface dialogInterface, int i)
                         {
                             Toast.makeText(mContext,"https://www.youtube.com/watch?v=Q0jr7vRH7_s!!!",Toast.LENGTH_SHORT).show();
                         }

                     }
             )
.show();
    }*/

    public void ShowMyNot(){
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        /* 建立Notication，並設定相關參數 */
        String text;
        Notification myNot=new Notification();
        myNot.defaults = Notification.DEFAULT_ALL;
        text = "來吧!!!";
    /* 設定statusbar顯示的icon */
        myNot.icon=R.drawable.go;
    /* 設定statusbar顯示的文字訊息 */
        myNot.tickerText=text;
        myNot.setLatestEventInfo(getApplicationContext(), "肥宅該運動啦~~",
                text, pi);
        myNotManager.notify(0, myNot);
    }

    public  void showvideo(){
        
        Intent intent123 = new Intent();
        intent123.setAction(Intent.ACTION_VIEW);
        intent123.setPackage("com.google.android.youtube");
        intent123.setData(Uri.parse("https://www.youtube.com/watch?v=IZkYdqRWKaY"));
        intent123.putExtra("force_fullscreen", true);
        startActivity(intent123);

    }

    /*public void makeFinishToast(){                //倒數時間結束，跳出的訊息設定，UIHandler.java有call到

        //Video
        /* 全螢幕 */
       // getWindow().setFormat(PixelFormat.TRANSLUCENT);
       // setContentView(R.layout.activity_main);
		/* 判斷記憶卡是否存在 */
      /* if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            bIfSDExist = true;
        }
        else
        {
            bIfSDExist = false;
        }
        //video
        Myvideo01 = (VideoView)findViewById(R.id.Myvideo);
        strVideoPath = "/sdcard/VIDEO0021.mp4";
        playVideo(strVideoPath);
        //Video

        Toast myToast = new Toast(MainActivity.this);
        LinearLayout toastLayout = new LinearLayout(MainActivity.this);
        toastLayout.setOrientation(LinearLayout.VERTICAL);            //圖片與訊息垂直，若要水平則改成HORIZONTAL
        myToast.setGravity(Gravity.CENTER, 0, 0);                     //彈出訊息設置在中央
        ImageView toastImage = new ImageView(MainActivity.this);
        TextView toastText = new TextView(MainActivity.this);
        toastImage.setImageResource(R.drawable.ic_launcher);                   //圖片
        textView.setAutoLinkMask(Linkify.ALL);
        toastText.setText("Put down your phone and exercise!");         //文字訊息
        toastLayout.addView(toastImage);
        toastLayout.addView(toastText);
        myToast.setView(toastLayout);
        myToast.setDuration(Toast.LENGTH_LONG);                    //彈出訊息滯留時間，若要短一點可改成LENGTH_SHORT
        myToast.show();
    }*/

    //play video
    private void playVideo(String strPath)
    {
        if(strPath!="")
        {
      /* 呼叫VideoURI方法，指定解析路徑 */
            //mVideoView01.setVideoURI(Uri.parse(strPath));
            Myvideo01.setVideoURI(Uri.parse(strPath));

      /* 設定控制Bar顯示於此Context中 */
            //mVideoView01.setMediaController(new MediaController(MainActivity.this));
            //mVideoView01.requestFocus();
            Myvideo01.setMediaController(new MediaController(MainActivity.this));
            Myvideo01.requestFocus();

      /* 呼叫VideoView.start()自動播放 */
           //mVideoView01.start();
            Myvideo01.start();
        }
    }
    //play video

    //更改時間之處
    private void showTimerDialog() {
        // TODO Auto-generated method stub
        String stringTimer = "00:00:05";
        int intHour = 0;
        int intMin = 0;
        int intSec = 5;
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

}
