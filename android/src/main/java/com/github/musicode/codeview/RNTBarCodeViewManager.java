package com.github.musicode.codeview;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.github.herokotlin.code.BarCodeView;

public class RNTBarCodeViewManager extends SimpleViewManager<BarCodeView> {

    private final ReactApplicationContext reactContext;

    public RNTBarCodeViewManager(ReactApplicationContext reactContext) {
        super();
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNTBarCodeView";
    }

    @Override
    protected BarCodeView createViewInstance(ThemedReactContext reactContext) {
        return new BarCodeView(reactContext);
    }

    @ReactProp(name = "text")
    public void setText(BarCodeView view, String text) {
        view.setText(text);
    }

}