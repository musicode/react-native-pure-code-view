package com.github.musicode.codeview;

import android.view.View;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;
import com.github.herokotlin.code.CodeScanner;

class RNTCodeScanner extends CodeScanner implements LifecycleEventListener {

    private Boolean isStarted = false;

    private Boolean isResuming = false;

    public RNTCodeScanner(ThemedReactContext reactContext) {
        super(reactContext);
        reactContext.addLifecycleEventListener(this);
    }

    public void destroy() {
        stop();
        ((ThemedReactContext)getContext()).removeLifecycleEventListener(this);
    }

    public void forceUpdate() {
        measure(
            View.MeasureSpec.makeMeasureSpec(getWidth(), View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(getHeight(), View.MeasureSpec.EXACTLY)
        );
        layout(getLeft(), getTop(), getRight(), getBottom());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && isResuming && !isStarted) {
            isStarted = true;
            start();
            forceUpdate();
        }
    }

    @Override
    public void onHostResume() {
        isResuming = true;
        if (isStarted) {
            resume();
            forceUpdate();
        }
        else if (getWidth() > 0 && getHeight() > 0) {
            isStarted = true;
            start();
            forceUpdate();
        }
    }

    @Override
    public void onHostPause() {
        isResuming = false;
        pause();
    }

    @Override
    public void onHostDestroy() {

    }

}