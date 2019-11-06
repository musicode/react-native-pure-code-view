package com.github.musicode.codeview

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.github.herokotlin.code.BarCodeView

class RNTBarCodeViewManager(private val reactContext: ReactApplicationContext) : SimpleViewManager<BarCodeView>() {

    override fun getName(): String {
        return "RNTBarCodeView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): BarCodeView {
        return BarCodeView(reactContext)
    }

    @ReactProp(name = "text")
    fun setText(view: BarCodeView, text: String) {
        view.text = text
    }

}