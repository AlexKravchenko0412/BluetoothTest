package com.example.bluetoothtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView statusBT;
    private TextView paired;
    private Button btnTurnOn, btnTurnOff, btnDiscoverable, btnPaired;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    BluetoothAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusBT = findViewById(R.id.tvStatusBluetooth);
        paired = findViewById(R.id.tvPaired);
        btnTurnOn = findViewById(R.id.btnTurnOn);
        btnTurnOff = findViewById(R.id.btnTurnOff);
        btnDiscoverable = findViewById(R.id.btnDiscoverable);
        btnPaired = findViewById(R.id.btnPaired);

        adapter = BluetoothAdapter.getDefaultAdapter();


        if(adapter == null) {
            statusBT.setText("Bluetooth is not available");
        }
        else {
            statusBT.setText("Bluetooth is available");
        }

        if(adapter.isEnabled()) {
            showToast("Bluetooth is on");
        } else {
            showToast("Bluetooth is off");
        }


        btnTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!adapter.isEnabled()) {
                    showToast("Turning on bluetooth");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_ENABLE_BT);
                } else {
                    showToast("Bluetooth is already on");
                }
            }
        });

        btnDiscoverable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!adapter.isDiscovering()){
                    showToast("Making your device discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });


        btnTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.isEnabled()) {
                    adapter.disable();
                    showToast("Turning off bluetooth");
                } else {
                    showToast("Bluetooth is already off");
                }
            }
        });

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.isEnabled()) {
                    paired.setText("Paired devices");
                    Set<BluetoothDevice> devices = adapter.getBondedDevices();
                    for(BluetoothDevice device : devices) {
                        paired.append("\nDevice: " + device.getName() + "," + device);
                    }
                }
                else {
                    showToast("Turn on bluetooth to get paired devices");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       switch (requestCode) {
           case REQUEST_ENABLE_BT:
               if(resultCode == RESULT_OK) {
                   showToast("Bluetooth is on");
               } else {
                   showToast("Couldn't on bluetooth");
               }
               break;
       }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}