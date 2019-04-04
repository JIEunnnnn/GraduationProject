package com.example.owner.project_final;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;


public class BluetoothLED extends AppCompatActivity {


    Button red, green, yellow, off;
    TextView txtArduino;
    RelativeLayout rlayout;

    Handler h; // 다른 객체들이 보낸 데이터를 받고 처리하는 객체

    final int RECEIVE_MESSAGE = 1;
    private BluetoothAdapter btadapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    // 블루투스로 받은 데이터를 저장하는 곳
    private static int flag = 0;
    // flag가 0,1,2,3,에 띠라 불빛 변경

    private ConnectedThread mConnectedThread; // 클래스 정의

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static String address = "98:D3:61:FD:4F:93";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_led);



        red = (Button) findViewById(R.id.red);
        green = (Button) findViewById(R.id.green);
        yellow = (Button) findViewById(R.id.yellow);

        off = (Button) findViewById(R.id.off);

        txtArduino = (TextView) findViewById(R.id.txtArduino);
        rlayout = (RelativeLayout)findViewById(R.id.layout);

        h = new Handler() { // 핸들러는 다른 장치로부터 데이터를 받거나 보내는 역할을 수행
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RECEIVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;

                        String strIncom = new String(readBuf, 0, msg.arg1);

                        sb.append(strIncom);
                        int endOfLineIndex = sb.indexOf("\r\n");
                        if (endOfLineIndex > 0) {
                            String sbPrint = sb.substring(0, endOfLineIndex);
                            sb.delete(0, sb.length());
                            txtArduino.setText("Data From Arduino: " + sbPrint);

                            if (flag % 4 == 3) {
                                rlayout.setBackgroundColor(Color.rgb(255, 255, 255));
                            } else if (flag % 4 == 1) {
                                rlayout.setBackgroundColor(Color.rgb(255, 0, 0));
                            } else if (flag % 4 == 2) {
                                rlayout.setBackgroundColor(Color.rgb(0, 255, 0));
                            } else if (flag % 4 == 0) {
                                rlayout.setBackgroundColor(Color.rgb(0, 0, 255));
                            }
                            flag++;
                            red.setEnabled(true);
                            green.setEnabled(true);
                            yellow.setEnabled(true);
                            off.setEnabled(true);
                        }
                        break;


                }


            }
        };

        btadapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState(); // 메소드


        red.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1");
                //Toast.makeText(getBaseContext(), "Turn on First LED", Toast.LENGTH_SHORT).show();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("2");
                //Toast.makeText(getBaseContext(), "Turn on Second LED", Toast.LENGTH_SHORT).show();
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("3");
                //Toast.makeText(getBaseContext(), "Turn on Third LED", Toast.LENGTH_SHORT).show();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("0");
                //Toast.makeText(getBaseContext(), "Turn on all LEDs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException{

        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e("TAG", "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("tag", "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btadapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.


        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btadapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d("TAG", "...Connecting...");
        try {
            btSocket.connect();
            Log.d("TAG", "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d("TAG", "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("TAG", "...In onPause()...");

        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }


    private void checkBTState(){

        if(btadapter==null){
            System.out.println("adapter없음");
        }else{
            if(btadapter.isEnabled()){
                System.out.println("bluetooth켬");
            }
            else{
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);

            }
        }
    }

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title+"-"+message, Toast.LENGTH_LONG).show();
        finish();
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private  final InputStream mmInStream ;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket){
            mmSocket = socket;
            InputStream tmpIn = null ;
            OutputStream tmpOut = null;

            try{
                tmpIn = mmSocket.getInputStream();
                tmpOut =mmSocket.getOutputStream();
            }catch(IOException e){

            }
            mmInStream =tmpIn;
            mmOutStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[256];
            int bytes;

            while(true){
                try{
                    bytes = mmInStream.read(buffer);
                    h.obtainMessage(RECEIVE_MESSAGE,bytes, -1, buffer).sendToTarget();
                }catch (IOException e){
                    break;
                }
            }
        }

        public void write(String message){
            Log.d("TAG", "DAta to send:"+message+"...");
            byte[] msgBuffer = message.getBytes();

            try{
                mmOutStream.write(msgBuffer);
            }catch (IOException e){
                Log.d("Tag", "error data send "+e.getMessage()+"...");
            }
        }



    }

}

