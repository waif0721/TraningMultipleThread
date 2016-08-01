package com.a.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Button mLoadImageButton;
    private Button mShowToastButton;
    private ProgressBar mProgessBar;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mProgessBar.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mProgessBar.setProgress((int)msg.obj);
                    break;
                case 2:
                    mImageView.setImageBitmap((Bitmap) msg.obj);
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.activity_main_image_view);
        mLoadImageButton = (Button) findViewById(R.id.activity_main_load_image_button);
        mShowToastButton = (Button) findViewById(R.id.activity_main_show_toast_button);
        mProgessBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

        mLoadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Message msg=mHandler.obtainMessage();
                                msg.what=0;
                                mHandler.sendMessage(msg);
                                for(int i=1;i<11;i++){
                                    sleep();
                                    Message msg2=mHandler.obtainMessage();
                                    msg2.what=1;
                                    msg2.obj=i*10;
                                    mHandler.sendMessage(msg2);
                                }
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                        R.drawable.ic_launcher);
                                Message msgBitMap=mHandler.obtainMessage();
                                msgBitMap.what=2;
                                msgBitMap.obj=bitmap;
                                mHandler.sendMessage(msgBitMap);

                            }

                            private void sleep() {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();
            }
        });
        mShowToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "aaaa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
