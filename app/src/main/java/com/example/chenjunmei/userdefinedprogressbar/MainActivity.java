package com.example.chenjunmei.userdefinedprogressbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * create by junmei
 * 自定义圆形ProgressBar的使用，并让其转起来
 */
public class MainActivity extends Activity {
    private MyProgressBar myProgressBar;
    private int totalProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myProgressBar= (MyProgressBar) findViewById(R.id.myprogressbar);

        //直接根据自定义的属性来设置进度
        totalProgress=90;

        //在子线程中让progressBar动态加载-->涉及耗时的操作都要放在子线程中去做
        new Thread(runnable).start();

    }

    //进度条的加载
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            myProgressBar.setProgress(0);
            myProgressBar.setMax(100);//可修改max值
            for (int i = 0; i < totalProgress; i++) {
                myProgressBar.setProgress(myProgressBar.getProgress() + 1);
                SystemClock.sleep(50);

                //刷新界面
                myProgressBar.postInvalidate();
            }
        }
    };

}
