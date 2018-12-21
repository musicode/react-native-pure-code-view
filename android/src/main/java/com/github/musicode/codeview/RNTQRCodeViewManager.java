package com.github.musicode.codeview;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.github.herokotlin.code.QRCodeView;

public class RNTQRCodeViewManager extends SimpleViewManager<QRCodeView> {

    private final ReactApplicationContext reactContext;

    public RNTQRCodeViewManager(ReactApplicationContext reactContext) {
        super();
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNTQRCodeView";
    }

    @Override
    protected QRCodeView createViewInstance(ThemedReactContext reactContext) {
        return new QRCodeView(reactContext);
    }

    @ReactProp(name = "text")
    public void setText(QRCodeView view, String text) {
        view.setText(text);
    }

}