package com.github.musicode.codeview;

import android.app.Activity;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.github.herokotlin.code.CodeScannerCallback;

import java.util.Map;

public class RNTCodeScannerManager extends SimpleViewManager<RNTCodeScanner> {

    private final ReactApplicationContext reactAppContext;

    public RNTCodeScannerManager(ReactApplicationContext reactContext) {
        super();
        this.reactAppContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNTCodeScanner";
    }

    @Override
    protected RNTCodeScanner createViewInstance(final ThemedReactContext reactContext) {
        final RNTCodeScanner scanner = new RNTCodeScanner(reactContext);
        final PermissionListener listener = new PermissionListener() {
            @Override
            public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                scanner.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return true;
            }
        };

        scanner.callback = new CodeScannerCallback() {
            @Override
            public void onScanSuccess(String text) {
                WritableMap event = Arguments.createMap();
                event.putString("text", text);
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(scanner.getId(), "onScanSuccess", event);
            }

            @Override
            public void onRequestPermissions(Activity activity, String[] permissions, int requestCode) {
                if (activity instanceof ReactActivity) {
                    ((ReactActivity)activity).requestPermissions(permissions, requestCode, listener);
                }
                else if (activity instanceof PermissionAwareActivity) {
                    ((PermissionAwareActivity)activity).requestPermissions(permissions, requestCode, listener);
                }
            }

            @Override
            public void onPermissionsNotGranted() {
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(scanner.getId(), "onPermissionsNotGranted", null);
            }

            @Override
            public void onPermissionsGranted() {
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(scanner.getId(), "onPermissionsGranted", null);
            }

            @Override
            public void onPermissionsDenied() {
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(scanner.getId(), "onPermissionsDenied", null);
            }

        };

        return scanner;
    }

    @ReactProp(name = "title")
    public void setTitle(RNTCodeScanner view, String title) {
        view.setGuideTitle(title);
    }

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("onScanSuccess", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onScanSuccess")))
                .put("onPermissionsNotGranted", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onPermissionsNotGranted")))
                .put("onPermissionsGranted", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onPermissionsGranted")))
                .put("onPermissionsDenied", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onPermissionsDenied")))
                .build();
    }

    @Override
    public void onDropViewInstance(RNTCodeScanner view) {
        super.onDropViewInstance(view);
        view.destroy();
    }

}