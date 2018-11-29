package com.call;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.call.activity.service.BackService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "jsh";
    private Intent mServiceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String string = "shutdown";
                try {
                    Log.i(TAG, "是否为空：" + binder);
                    if (binder == null) {
                        Toast.makeText(getApplicationContext(),
                                "没有连接，可能是服务器已断开", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean isSend = binder.sendMessage(string);
                        Toast.makeText(MainActivity.this,
                                isSend ? "success" : "fail", Toast.LENGTH_SHORT)
                                .show();
//                        et.setText("");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        try {
//                            Socket socket = new Socket("192.168.31.101", 8888);
//                            boolean connected = socket.isConnected();
//                            Log.i("info","connected?" + connected);
//                            OutputStream os = socket.getOutputStream();
//                            PrintWriter pw = new PrintWriter(os);
//                            pw.write("shutdown");
//                            pw.flush();
//                            pw.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//
//                thread.start();

            }
        });

    }
    private void initData() {
        mServiceIntent = new Intent(this, BackService.class);

//        btn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String string = et.getText().toString().trim();
//                Log.i(TAG, string);
//
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("jsh","onStart");
//        bindService(mServiceIntent, conn, BIND_AUTO_CREATE);
        // 开始服务
//        registerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("jsh","onResume");
        // 注册广播 最好在onResume中注册
        // registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("jsh","onPause");
        // 注销广播 最好在onPause上注销
//        unregisterReceiver(mReceiver);
        // 注销服务
//        unbindService(conn);
    }

    // 注册广播
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BackService.HEART_BEAT_ACTION);
        intentFilter.addAction(BackService.MESSAGE_ACTION);
        registerReceiver(mReceiver, intentFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 消息广播
            if (action.equals(BackService.MESSAGE_ACTION)) {
                String stringExtra = intent.getStringExtra("message");
                Log.d("jsh","stringExtra:"+stringExtra);
//                tv.setText(stringExtra);
            } else if (action.equals(BackService.HEART_BEAT_ACTION)) {// 心跳广播
//                tv.setText("正常心跳");
            }
        }
    };
    private BackService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 未连接为空
            binder = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 已连接
            binder=(BackService.MyBinder) service;
        }
    };
}
