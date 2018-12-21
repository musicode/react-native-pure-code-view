package com.github.musicode.codeview;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.view.View;

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
import com.github.herokotlin.code.CodeScannerConfiguration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RNTCodeScannerViewManager extends SimpleViewManager<RNTCodeScanner> {

    private final ReactApplicationContext reactAppContext;

    public RNTCodeScannerViewManager(ReactApplicationContext reactContext) {
        super();
        this.reactAppContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNTCodeScannerView";
    }

    @Override
    protected RNTCodeScanner createViewInstance(final ThemedReactContext reactContext) {
        final RNTCodeScanner scanner = new RNTCodeScanner(reactContext);
        final PermissionListener listener = new PermissionListener() {
            @Override
            public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                scanner.requestPermissionsResult(requestCode, permissions, grantResults);
                return true;
            }
        };

        scanner.init(
            new CodeScannerConfiguration(scanner.getContext()) {
                @Override
                public boolean requestPermissions(@NotNull List<String> permissions, int requestCode) {

                    List<String> list = new ArrayList<>();

                    for (String permission: permissions) {
                        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                            list.add(permission);
                        }
                    }

                    if (list.size() > 0) {
                        Activity activity = reactAppContext.getCurrentActivity();
                        if (activity instanceof ReactActivity) {
                            ((ReactActivity)activity).requestPermissions(list.toArray(new String[list.size()]), requestCode, listener);
                        }
                        else if (activity instanceof PermissionAwareActivity) {
                            ((PermissionAwareActivity)activity).requestPermissions(list.toArray(new String[list.size()]), requestCode, listener);
                        }
                        return false;
                    }

                    return true;

                }
            },
            new CodeScannerCallback() {
                @Override
                public void onScanSuccess(@NotNull String text) {
                    WritableMap event = Arguments.createMap();
                    event.putString("text", text);
                    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(scanner.getId(), "onScanSuccess", event);
                }

                @Override
                public void onScanWithoutPermissions() {
                    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(scanner.getId(), "onScanWithoutPermissions", null);
                }

                @Override
                public void onPermissionsGranted() {
                    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(scanner.getId(), "onPermissionsGranted", null);
                }

                @Override
                public void onPermissionsDenied() {
                    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(scanner.getId(), "onPermissionsDenied", null);
                }

                @Override
                public void onSizeChange() {
                    scanner.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scanner.measure(
                                    View.MeasureSpec.makeMeasureSpec(scanner.getWidth(), View.MeasureSpec.EXACTLY),
                                    View.MeasureSpec.makeMeasureSpec(scanner.getHeight(), View.MeasureSpec.EXACTLY)
                            );
                            scanner.layout(scanner.getLeft(), scanner.getTop(), scanner.getRight(), scanner.getBottom());
                        }
                    }, 500);
                }
            }
        );

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
                .put("onScanWithoutPermissions", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onScanWithoutPermissions")))
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