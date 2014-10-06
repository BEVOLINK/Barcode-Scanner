Barcode Scanner
===============

## Bevolink Barcode Scanner versions

-  <a href="http://www.aliexpress.com/item/USB-Laser-Barcode-Scanner-USB-Barcode-Reader-Decoder-Wire-RS232-HID-OEM-Retail-Symbol-CCD-POS/2046279627.html">USB barcode scanner</a>
-  <a href="http://www.aliexpress.com/item/NEW-2-4G-High-Quality-Long-Laser-USB-Port-CCD-Handheld-POS-Barcode-Scanner-Reader-Handheld/2046236437.html">2.4G Wireless barcode scanner</a>
-  <a href="http://www.aliexpress.com/item/500M-433MHZ-Barcode-Scanner-Reader-Wireless-Long-Distance-USB-Handheld-barcode-data-collector-POS-OEM-Inventory/2046279058.html">RF 433 MHz Wireless Scanner</a>
-  <a href="http://www.aliexpress.com/item/Wireless-Bluetooth-Laser-Barcode-Scanner-Reader-Handheld-Wireless-Wired-HID-POS-for-Apple-iOS-Android-Windows/2046264504.html">Bluetooth Wireless Scanner</a>  



## Configure SPP mode for Barcode Scanner
Print the commands barcode in a paper, and scan "%%BT_SPP" command by the scanner device, then it work on SPP mode.

<p>Download for Bluetooth Scanner<a href="https://github.com/BEVOLINK/Barcode-Scanner/blob/master/Command%20Barcode%20For%20Bluetooth%20Scanner_EN.pdf"> barcode commands </a></p>
<p>Download for 2.4G or 433 Scanner<a href="https://github.com/BEVOLINK/Barcode-Scanner/raw/master/Command%20Barcode%202.4G_433_EN.pdf"> barcode commands </a></p>

## Android SDK
The SDK and sample work in Bluetooth Wireless Scanner， It demonstrates how to code in Android device for the barcode scanner.

The features in the sample:
- Auto connect to the Bluetooth Barcode Scanner
- Catch the barcode code number via SPP
- Count the total of the inputs

<img src="https://raw.githubusercontent.com/BEVOLINK/Barcode-Scanner/master/Android/AndroidSPP_SampleApp.png"/>

The app can not work in some SAMSUNG Android device since the driver has some bugs.  

## iOS
<p>Only Bluetooth Wireless Scanner supports iOS via HID interface. </p>
<p>The BLE Scanner is developing ... </p>


## Windows
The sample supports 2.4G Wireless,RF 433 MHz Wireless, Bluetooth Wireless, the USB dongle will work on virtual serial port mode.

