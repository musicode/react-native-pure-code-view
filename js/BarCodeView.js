import React, {
  PureComponent,
} from 'react'

import {
  requireNativeComponent,
} from 'react-native'

import PropTypes from 'prop-types'

class BarCodeView extends PureComponent {

  static propTypes = {
    text: PropTypes.string.isRequired,
    style: PropTypes.any,
  }

  render() {
    return (
      <RNTBarCodeView
        {...this.props}
      />
    )
  }

}

const RNTBarCodeView = requireNativeComponent('RNTBarCodeView', BarCodeView)

export default BarCodeView
