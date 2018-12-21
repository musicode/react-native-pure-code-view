import React, {
  Component,
} from 'react'

import {
  requireNativeComponent,
  ViewPropTypes,
} from 'react-native'

import PropTypes from 'prop-types'

class QRCodeView extends Component {

  static propTypes = {
    text: PropTypes.string.isRequired,
    style: ViewPropTypes.style,
  }

  render() {
    return (
      <RNTQRCodeView
        {...this.props}
      />
    )
  }

}

const RNTQRCodeView = requireNativeComponent('RNTQRCodeView', QRCodeView)

export default QRCodeView
