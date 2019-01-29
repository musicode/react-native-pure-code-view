import React, {
  Component,
} from 'react'

import {
  requireNativeComponent,
  ViewPropTypes,
} from 'react-native'

import PropTypes from 'prop-types'

class CodeScanner extends Component {

  static propTypes = {
    title: PropTypes.string.isRequired,
    style: ViewPropTypes.style,
    onScanSuccess: PropTypes.func,
    onScanWithoutPermissions: PropTypes.func,
    onPermissionsGranted: PropTypes.func,
    onPermissionsDenied: PropTypes.func,
  }

  handleScanSuccess = event => {
    let { onScanSuccess } = this.props
    if (onScanSuccess) {
      onScanSuccess(event.nativeEvent)
    }
  }

  handleScanWithoutPermissions = event => {
    let { onScanWithoutPermissions } = this.props
    if (onScanWithoutPermissions) {
      onScanWithoutPermissions(event.nativeEvent)
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
        onScanWithoutPermissions={this.handleScanWithoutPermissions}
        onPermissionsGranted={this.handlePermissionsGranted}
        onPermissionsDenied={this.handlePermissionsDenied}
      />
    )
  }

}

const RNTCodeScanner = requireNativeComponent('RNTCodeScanner', CodeScanner)

export default CodeScanner
