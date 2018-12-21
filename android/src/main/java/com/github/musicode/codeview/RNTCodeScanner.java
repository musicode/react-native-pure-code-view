package com.github.musicode.codeview;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;
import com.github.herokotlin.code.CodeScanner;

class RNTCodeScanner extends CodeScanner implements LifecycleEventListener {

    public RNTCodeScanner(ThemedReactContext reactContext) {
        super(reactContext);
        reactContext.addLifecycleEventListener(this);
    }

    public void destroy() {
        pause();
        ((ThemedReactContext)getContext()).removeLifecycleEventListener(this);
    }

    @Override
    public void onHostResume() {
        resume();
    }

    @Override
    public void onHostPause() {
        pause();
    }

    @Override
    public void onHostDestroy() {

    }
}