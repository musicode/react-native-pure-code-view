/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View} from 'react-native';

import {
  BarCodeView,
  QRCodeView,
  CodeScanner,
} from 'react-native-pure-code-view'

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {
  render() {
    return (
      <View style={styles.container}>
        <BarCodeView
          text="1213123123123"
          style={styles.barCodeView}
        />
        <QRCodeView
          text="1213123123123"
          style={styles.qrCodeView}
        />
        <CodeScanner
          title="123123"
          style={styles.scanner}
          onScanSuccess={event => {
            console.log('onScanSuccess', event)
          }}
          onScanWithoutPermissions={event => {
            console.log('onScanWithoutPermissions')
          }}
          onPermissionsGranted={event => {
            console.log('onPermissionsGranted', event)
          }}
          onPermissionsDenied={event => {
            console.log('onPermissionsDenied', event)
          }}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
  },
  barCodeView: {
    width: 160,
    height: 40,
  },
  qrCodeView: {
    width: 100,
    height: 100,
  },
  scanner: {
    flex: 1,
    backgroundColor: '#eee',
  }
});
