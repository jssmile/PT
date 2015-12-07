package com.example.learnservice;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;

public class CustomDialogActivity extends Activity {

    TextView text_title;
    ArrayAdapter<String> VideoList;
    private  String[] video={"請選擇","TABATA","腹肌","鄭多燕有氧"};
    private Vibrator Vib;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        text_title  = (TextView)findViewById(R.id.text_title);

        //Vibration
        Vib = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        Vib.vibrate(new long[]{1000, 100, 1000, 500}, -1);

        //Spinner
        Spinner spinner = (Spinner)findViewById(R.id.MySpinner);
        VideoList = new ArrayAdapter<String>(CustomDialogActivity.this, android.R.layout.simple_spinner_item, video);
        spinner.setAdapter(VideoList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long arg3) {

                switch (video[position]) {
                    case "TABATA":
                        //Youtube
                        Intent intent1 = new Intent();
                        intent1.setAction(Intent.ACTION_VIEW);
                        intent1.setPackage("com.google.android.youtube");
                        intent1.setData(Uri.parse("https://www.youtube.com/watch?v=B4MsfoosrHU"));
                        intent1.putExtra("force_fullscreen", true);
                        startActivity(intent1);
                        finish();
                        break;
                    case "腹肌":
                        //Youtube
                        Intent intent2 = new Intent();
                        intent2.setAction(Intent.ACTION_VIEW);
                        intent2.setPackage("com.google.android.youtube");
                        intent2.setData(Uri.parse("https://www.youtube.com/watch?v=MCqUyP6ZjJY"));
                        intent2.putExtra("force_fullscreen", true);
                        startActivity(intent2);
                        finish();
                        break;
                    case "鄭多燕有氧":
                        //Youtube
                        Intent intent3 = new Intent();
                        intent3.setAction(Intent.ACTION_VIEW);
                        intent3.setPackage("com.google.android.youtube");
                        intent3.setData(Uri.parse("https://www.youtube.com/watch?v=abiT6VJCPrI"));
                        intent3.putExtra("force_fullscreen", true);
                        startActivity(intent3);
                        finish();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(CustomDialogActivity.this, "你他媽根本沒選", Toast.LENGTH_SHORT).show();
            }
        });
    }
}