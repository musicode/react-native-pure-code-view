package com.github.musicode.codeview

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.github.herokotlin.code.QRCodeView

class RNTQRCodeViewManager(private val reactContext: ReactApplicationContext) : SimpleViewManager<QRCodeView>() {

    override fun getName(): String {
        return "RNTQRCodeView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): QRCodeView {
        return QRCodeView(reactContext)
    }

    @ReactProp(name = "text")
    fun setText(view: QRCodeView, text: String) {
        view.text = text
    }

}