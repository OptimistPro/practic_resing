package com.test.resing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class game extends AppCompatActivity  implements View.OnTouchListener {
    boolean start_p = false;
    int pos_x = 0;
    int pos_x2 = 0;
    int speed = 0;
    int speed2 =0;
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //скрытие панель навигации
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);




        //обработка нажатий

        Button button1 = findViewById(R.id.start);
        View.OnTouchListener st = new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event)
            {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    speed2=1;
                    pos_x=0;
                    pos_x2=0;
                    start_p=true;
                }
                return true;
            }

        };

        button1.setOnTouchListener(st);

        Button button2 = findViewById(R.id.drive);
        View.OnTouchListener dr = new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event)
            {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    if (start_p) {
                        speed = 2;
                    }
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    speed=0;
                }

                return true;
            }

        };

        button2.setOnTouchListener(dr);

        //Создаём поток
        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                    pos_x += speed;
                                    ImageView player = (ImageView) findViewById(R.id.player);
                                    player.setLeft(pos_x);


                                    pos_x2 += speed2;
                                    ImageView player2 = (ImageView) findViewById(R.id.vrag);
                                    player2.setLeft(pos_x2);

                                ImageView fin = (ImageView) findViewById(R.id.finish);

                                    if (pos_x>fin.getLeft()||pos_x2>fin.getLeft()){
                                        speed2=0;
                                        speed=0;
                                        pos_x=0;
                                        pos_x2=0;
                                        start_p=false;
                                    }

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

    }

    public boolean onTouch(View view, MotionEvent event)
    {
        return true;
    }



}