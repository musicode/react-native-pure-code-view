import React, {
  Component,
} from 'react'

import {
  requireNativeComponent,
  ViewPropTypes,
} from 'react-native'

import PropTypes from 'prop-types'

class BarCodeView extends Component {

  static propTypes = {
    text: PropTypes.string.isRequired,
    style: ViewPropTypes.style,
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
