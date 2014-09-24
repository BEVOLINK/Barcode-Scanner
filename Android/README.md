Barcode Scanner SDK & Samples for Android
===============


<img src="https://raw.githubusercontent.com/BEVOLINK/Barcode-Scanner/master/Android/AndroidSPP_SampleApp.png" alt="SPP" title="Android SPP">
As known, some SAMSUNG Android Phone's driver has bugs, the app can not work.Others android devices works.

#### Example

```java
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
```
