# react-native-pure-code-view

There are three native views in this module.

* BarCodeView: a barcode generator
* QRCodeView: a qrcode generator
* CodeScanner: a scanner which can recognizes barcode and qrocde

## Installation

```
npm i react-native-pure-code-view
// link below 0.60
react-native link react-native-pure-code-view
```

## Setup

### iOS

Add `NSCameraUsageDescription` in your `ios/${ProjectName}/Info.plist`:

```
<key>NSCameraUsageDescription</key>
<string>balabala</string>
```

### Android

Add `jitpack` in your `android/build.gradle` at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

## Usage

```js
import {
  BarCodeView,
  QRCodeView,
  CodeScanner,
} from 'react-native-pure-code-view'

// BarCodeView
<BarCodeView
  text="xx",
  style={styles.barcode}
/>

// QRCodeView
<QRCodeView
  text="xx",
  style={styles.qrcode}
/>

// CodeScanner
<QRCodeView
  title="将二维码放入框内，即可自动扫描"
  onScanSuccess={this.handleScanSuccess}
  onPermissionsNotGranted={this.handlePermissionsNotGranted}
  onPermissionsGranted={this.handlePermissionsGranted}
  onPermissionsDenied={this.handlePermissionsDenied}
  style={styles.codeScanner}
/>
