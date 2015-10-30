package com.example.learnservice;

/**
 * Created by user on 2015/10/27.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.net.Uri;
import android.graphics.PixelFormat;
import android.widget.Toast;

public class Notification extends Activity{
    private  VideoView Myvideo01;
    private String strVideoPath = "";

    /* 預設判別是否安裝記憶卡flag為false */
    private boolean bIfSDExist = false;
    //Video
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);


    }
    public void makeFinishToast(){                //倒數時間結束，跳出的訊息設定，UIHandler.java有call到

        //Video
        /* 全螢幕 */
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.custom_dialog);
		/* 判斷記憶卡是否存在 */
       if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
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
    }

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
            Myvideo01.setMediaController(new MediaController(Notification.this));
            Myvideo01.requestFocus();

      /* 呼叫VideoView.start()自動播放 */
            //mVideoView01.start();
            Myvideo01.start();
        }
    }
    //play video

}
