
package com.bevolink.bluetoothterminal;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bevolink.bluetooth.BluetoothSPP;
import com.bevolink.bluetooth.BluetoothSPP$BluetoothConnectionListener;
import com.bevolink.bluetooth.BluetoothSPP$BluetoothStateListener;
import com.bevolink.bluetooth.BluetoothSPP$OnDataReceivedListener;
import com.bevolink.bluetooth.BluetoothState;
import com.bevolink.bluetooth.DeviceList;



public class MainActivity extends Activity {
	
	BluetoothSPP bt;
	
	Thread backgroundThread;
	
	boolean isRunning = false, isStopBackgroundThread = false;
	
	TextView textStatus, textRead,textTotalTicket;
	
	Menu menu;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   
		
		setContentView(R.layout.layout_main);

		textRead = (TextView)findViewById(R.id.textRead);
		textStatus = (TextView)findViewById(R.id.textStatus);
		textTotalTicket = (TextView)findViewById(R.id.txtTotalTicket);
		
		bt = new BluetoothSPP(this);

		if(!bt.isBluetoothAvailable()) {
			Toast.makeText(getApplicationContext()
					, getString(R.string.label_no_bluetooth)
					, Toast.LENGTH_SHORT).show();
            finish();
		}
		
		bt.setBluetoothStateListener(new BluetoothSPP$BluetoothStateListener(){

			@Override
			public void onServiceStateChanged(int state) {
				if (state == BluetoothState.STATE_CONNECTED) {
					isRunning = false;
				} else {
					isRunning = true;
				}
			}
			
		});
		
		bt.setOnDataReceivedListener(new BluetoothSPP$OnDataReceivedListener() {
			public void onDataReceived(byte[] data, String message) {
				addTicketNumber(message);
			}
		});
		
		bt.setBluetoothConnectionListener(new BluetoothSPP$BluetoothConnectionListener() {
			public void onDeviceDisconnected() {
				textStatus.setText(getString(R.string.label_no_connection));
				menu.clear();
				getMenuInflater().inflate(R.menu.connection, menu);
			}
			
			public void onDeviceConnectionFailed() {
				textStatus.setText(getString(R.string.label_failed_connection));
			}
			
			public void onDeviceConnected(String name, String address) {
				textStatus.setText(getString(R.string.label_connected) + name);
				menu.clear();
				getMenuInflater().inflate(R.menu.disconnection, menu);
			}
		});
		
		backgroundThread = new Thread  (backgroundRunnable);
		backgroundThread.start();
		isRunning = true;
	}
	
	
	private void addTicketNumber(String ticket){
		textRead.append(ticket + "\n");
		String strTotal = (String) textTotalTicket.getText();
		int total= Integer.parseInt(strTotal);
		textTotalTicket.setText(String.valueOf(total+1));
	}
	
	private Runnable backgroundRunnable = new Runnable() {
		@Override
		public void run() {
			while (!isStopBackgroundThread) {
				
				if(!bt.isAutoConnecting() && !bt.isConnected()){
					bt.autoConnect("SPP Barcode");
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.connection, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.menu_device_connect) {
			bt.setDeviceTarget(BluetoothState.DEVICE_ANDROID);
			Intent intent = new Intent(getApplicationContext(), DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);     
		} else if(id == R.id.menu_device_connect) {
			bt.setDeviceTarget(BluetoothState.DEVICE_OTHER);
			Intent intent = new Intent(getApplicationContext(), DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);     
		} 		
		return super.onOptionsItemSelected(item);
	}

	public void onDestroy() {
        super.onDestroy();
        bt.stopService();
        isStopBackgroundThread = true;
    }
	
	public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
        	Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) { 
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                setup();
            }
        }
    }
	
	 
	public void setup() {
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
			if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
		} else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                		, getString(R.string.label_no_bluetooth)
                		, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
	
}

