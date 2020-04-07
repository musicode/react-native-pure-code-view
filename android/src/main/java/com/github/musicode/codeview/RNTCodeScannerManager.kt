package com.github.musicode.codeview

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.github.herokotlin.code.CodeScannerCallback

class RNTCodeScannerManager(private val reactAppContext: ReactApplicationContext) : SimpleViewManager<RNTCodeScanner>() {

    override fun getName(): String {
        return "RNTCodeScanner"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): RNTCodeScanner {
        val scanner = RNTCodeScanner(reactContext)

        scanner.callback = object : CodeScannerCallback {

            override fun onScanSuccess(text: String) {
                val event = Arguments.createMap()
                event.putString("text", text)
                reactContext.getJSModule(RCTEventEmitter::class.java).receiveEvent(scanner.id, "onScanSuccess", event)
            }

        }

        return scanner
    }

    @ReactProp(name = "title")
    fun setTitle(view: RNTCodeScanner, title: String) {
        view.guideTitle = title
    }

    override fun getExportedCustomBubblingEventTypeConstants(): MutableMap<String, Any> {
        return MapBuilder.builder<String, Any>()
                .put("onScanSuccess", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onScanSuccess")))
                .build()
    }

    override fun onDropViewInstance(view: RNTCodeScanner) {
        super.onDropViewInstance(view)
        view.destroy()
    }

}