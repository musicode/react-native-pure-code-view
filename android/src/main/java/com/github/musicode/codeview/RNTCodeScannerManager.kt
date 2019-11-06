package com.github.musicode.codeview

import android.app.Activity

import com.facebook.react.ReactActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.common.MapBuilder
import com.facebook.react.modules.core.PermissionAwareActivity
import com.facebook.react.modules.core.PermissionListener
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
        val listener = PermissionListener { requestCode, permissions, grantResults ->
            scanner.onRequestPermissionsResult(requestCode, permissions, grantResults)
            true
        }

        scanner.callback = object : CodeScannerCallback {

            override fun onScanSuccess(text: String) {
                val event = Arguments.createMap()
                event.putString("text", text)
                reactContext.getJSModule(RCTEventEmitter::class.java).receiveEvent(scanner.id, "onScanSuccess", event)
            }

            override fun onRequestPermissions(activity: Activity, permissions: Array<out String>, requestCode: Int) {
                if (activity is ReactActivity) {
                    activity.requestPermissions(permissions, requestCode, listener)
                } else if (activity is PermissionAwareActivity) {
                    (activity as PermissionAwareActivity).requestPermissions(permissions, requestCode, listener)
                }
            }

            override fun onPermissionsNotGranted() {
                reactContext.getJSModule(RCTEventEmitter::class.java).receiveEvent(scanner.id, "onPermissionsNotGranted", null)
            }

            override fun onPermissionsGranted() {
                reactContext.getJSModule(RCTEventEmitter::class.java).receiveEvent(scanner.id, "onPermissionsGranted", null)
            }

            override fun onPermissionsDenied() {
                reactContext.getJSModule(RCTEventEmitter::class.java).receiveEvent(scanner.id, "onPermissionsDenied", null)
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
                .put("onPermissionsNotGranted", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onPermissionsNotGranted")))
                .put("onPermissionsGranted", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onPermissionsGranted")))
                .put("onPermissionsDenied", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onPermissionsDenied")))
                .build()
    }

    override fun onDropViewInstance(view: RNTCodeScanner) {
        super.onDropViewInstance(view)
        view.destroy()
    }

}