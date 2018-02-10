package com.example.pierre.projetandroidbarcode;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static android.os.Message.obtain;
import static android.provider.Settings.NameValueTable.NAME;

/**
 * Created by natha on 05/02/2018.
 */

public class BagarreActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    private BluetoothAdapter bluetoothAdapter;
    private UUID myUuid;
    private static final String TAG = "MY_APP_DEBUG_TAG";
    private Handler mHandler; // handler that gets info from Bluetooth service
    private List<BluetoothDevice> devices;
    private TextView fightTextView;
    private ImageView monstreView;

    // Defines several constants used when transmitting messages between the
    // service and the UI.
    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        fightTextView = (TextView) findViewById(R.id.fight);
        monstreView = (ImageView) findViewById(R.id.monstre);

        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        myUuid = java.util.UUID.fromString(tManager.getDeviceId());

        ask4Blu();

        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothReceiver, filter);
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        search4Dragons();

    }

    private void ask4Blu () {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH},
                    REQUEST_CODE_ENABLE_BLUETOOTH);
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
        }

    }

    private void search4Dragons(){
        bluetoothAdapter.startDiscovery();
        int device = 0;
        ConnectThread client = new ConnectThread(devices.get(device));

        while ( client.mmSocket == null && device<devices.size() ) {
            device++;
            client = new ConnectThread(devices.get(device));
        }
        if( client.mmSocket != null )
            client.run();
        Thread server = new AcceptThread();
        server.run();

    }

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Toast.makeText(BagarreActivity.this, "New Device = " + device.getName(), Toast.LENGTH_SHORT).show();
                devices.add(device);
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            if (resultCode == RESULT_OK) {
                Log.i("BAGARRE", "bluetoot activé");
            } else {
                Log.i("BAGARRE", "bluetoot pas activé");
            }
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothAdapter.cancelDiscovery();
        unregisterReceiver(bluetoothReceiver);
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, myUuid);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }

                if (socket != null) {
                    try {
                        runAsServer(socket);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket = null;
        private BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(myUuid);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            bluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }
            try {
                runAsClient(mmSocket);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }

    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);
                    // Send the obtained bytes to the UI activity.
                    Message readMsg = mHandler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer);
                    readMsg.sendToTarget();
                    
                    lancerBagarre(readMsg);
                    
                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }

        // Call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);

                // Share the sent message with the UI activity.
                Message writtenMsg = mHandler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                mHandler.sendMessage(writeErrorMsg);
            }
        }

        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }

    private void lancerBagarre(Message msg) {
        fightTextView.setText("FIGHT");
        String str = new String(msg.toString());
        try {
            JSONObject json = new JSONObject(str);
            int monstreId = (int) json.get("monstreId");

            MonstreBDD monstreBDD = new MonstreBDD(this);
            monstreBDD.open();

            monstreView.setImageResource(
                    this.getResources().getIdentifier(monstreBDD.getMonstreWithID(monstreId).getApparence().toLowerCase(), "drawable", getPackageName()));
            monstreBDD.close();
        } catch (JSONException e) {
            fightTextView.setText("ERROR JSON");
        }
    }

    private void runAsServer(BluetoothSocket socket) throws JSONException {
        ConnectedThread connection = new ConnectedThread(socket);
        connection.run();
        MonstreBDD monstreBDD = new MonstreBDD(this);
        monstreBDD.open();
        Monstre myMonstre = monstreBDD.getMonstreSelectionne();
        Arme myArme = monstreBDD.getArmeSelectionne();
        Armure myArmure = monstreBDD.getArmureSelectionne();
        JSONObject json = new JSONObject();
        json.put("monstreId", myMonstre.getId());
        json.put("Name", myMonstre.getNom());
        json.put("PDV", myMonstre.getPDV());
        json.put("PDA", myMonstre.getPDA()+myArme.getAttaque());
        json.put("def", myArmure.getDefense());
        monstreBDD.close();
        Message msg = obtain();
        msg.obj = json;
        connection.write(json.toString().getBytes());
    }

    private void runAsClient(BluetoothSocket socket) throws JSONException {
        ConnectedThread connection = new ConnectedThread(socket);
        connection.run();
        
    }
}
