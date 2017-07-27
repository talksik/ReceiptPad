package com.example.ram2.myreceipts;

/**
 * Created by RAM2 on 5/17/2017.
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

    public class Server extends Activity {

        private static ServerSocket serverSocket;
        private static Socket clientSocket;
        private static InputStream inputStream;
        private static FileOutputStream fileOutputStream;
        private static BufferedOutputStream bufferedOutputStream;
        private static int bufferSize = 3000; // bufferSize temporary hardcoded
        private static int bytesRead;
        private static int totalbytesRead = 0;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                init();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private void init() throws IOException {
            // TODO Auto-generated method stub
            serverSocket = new ServerSocket(5000); // Server socket

            System.out.println("Server started. Listening to the port 5000");

            clientSocket = serverSocket.accept();

            byte[] data = new byte[bufferSize]; // create byte array to buffer the
            // file

            inputStream = clientSocket.getInputStream();

            String filePath = "C:\\Users\\RAM2\\AndroidStudioProjects\\MyReceipts\\app\\src\\main\\res\\drawable\\bg_home.jpg" ;

            fileOutputStream = new FileOutputStream(filePath);
            // fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            System.out.println("Receiving...");

            // following lines read the input slide file byte by byte

            bytesRead = inputStream.read(data,0,data.length);
            totalbytesRead = bytesRead;



            do {
                bytesRead =
                        inputStream.read(data, totalbytesRead, (data.length-totalbytesRead));
                if(bytesRead >= 0) totalbytesRead += bytesRead;
            } while(bytesRead > -1);

            bufferedOutputStream.write(data, 0 , totalbytesRead);
            bufferedOutputStream.flush();
            long end = System.currentTimeMillis();
            System.out.println(end);
            bufferedOutputStream.close();
            System.out.println("Sever received the file");

        }
    }

