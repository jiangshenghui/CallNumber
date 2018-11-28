package com.call;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Socket socket = new Socket("192.168.31.101", 8888);
                            boolean connected = socket.isConnected();
                            Log.i("info","connected?" + connected);
                            OutputStream os = socket.getOutputStream();
                            PrintWriter pw = new PrintWriter(os);
                            pw.write("shutdown");
                            pw.flush();
                            pw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                thread.start();

            }
        });

    }
}
