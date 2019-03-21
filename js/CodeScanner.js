import React, {
  PureComponent,
} from 'react'

import {
  requireNativeComponent,
  ViewPropTypes,
} from 'react-native'

import PropTypes from 'prop-types'

class CodeScanner extends PureComponent {

  static propTypes = {
    title: PropTypes.string.isRequired,
    style: ViewPropTypes.style,
    onScanSuccess: PropTypes.func,
    onPermissionsNotGranted: PropTypes.func,
    onPermissionsGranted: PropTypes.func,
    onPermissionsDenied: PropTypes.func,
  }

  handleScanSuccess = event => {
    let { onScanSuccess } = this.props
    if (onScanSuccess) {
      onScanSuccess(event.nativeEvent)
    }
  }

  handlePermissionsNotGranted = event => {
    let { onPermissionsNotGranted } = this.props
    if (onPermissionsNotGranted) {
      onPermissionsNotGranted(event.nativeEvent)
    }
  }

  handlePermissionsGranted = event => {
    let { onPermissionsGranted } = this.props
    if (onPermissionsGranted) {
      onPermissionsGranted(event.nativeEvent)
    }
  }

  handlePermissionsDenied = event => {
    let { onPermissionsDenied } = this.props
    if (onPermissionsDenied) {
      onPermissionsDenied(event.nativeEvent)
    }
  }

  render() {
    return (
      <RNTCodeScanner
        {...this.props}
        onScanSuccess={this.handleScanSuccess}
        onPermissionsNotGranted={this.handlePermissionsNotGranted}
        onPermissionsGranted={this.handlePermissionsGranted}
        onPermissionsDenied={this.handlePermissionsDenied}
      />
    )
  }

}

const RNTCodeScanner = requireNativeComponent('RNTCodeScanner', CodeScanner)

export default CodeScanner
