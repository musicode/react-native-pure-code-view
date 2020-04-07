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
  }

  handleScanSuccess = event => {
    let { onScanSuccess } = this.props
    if (onScanSuccess) {
      onScanSuccess(event.nativeEvent)
    }
  }

  render() {
    return (
      <RNTCodeScanner
        {...this.props}
        onScanSuccess={this.handleScanSuccess}
      />
    )
  }

}

const RNTCodeScanner = requireNativeComponent('RNTCodeScanner', CodeScanner)

export default CodeScanner
