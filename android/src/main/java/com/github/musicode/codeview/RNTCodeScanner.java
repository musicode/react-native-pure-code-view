package com.github.musicode.codeview;

import android.view.Choreographer;
import android.view.View;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;
import com.github.herokotlin.code.CodeScanner;

class RNTCodeScanner extends CodeScanner implements LifecycleEventListener {

    private Boolean isStarted = false;

    private Boolean isResuming = false;

    private Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
                child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            }
            getViewTreeObserver().dispatchOnGlobalLayout();
            Choreographer.getInstance().postFrameCallback(this);
        }
    };

    public RNTCodeScanner(ThemedReactContext reactContext) {
        super(reactContext);
        setActivity(reactContext.getCurrentActivity());
        reactContext.addLifecycleEventListener(this);
        Choreographer.getInstance().postFrameCallback(frameCallback);
    }

    public void destroy() {
        stop();
        ((ThemedReactContext)getContext()).removeLifecycleEventListener(this);
        Choreographer.getInstance().removeFrameCallback(frameCallback);
        setActivity(null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && isResuming && !isStarted) {
            isStarted = true;
            start();
        }
    }

    @Override
    public void onHostResume() {
        isResuming = true;
        if (isStarted) {
            resume();
        }
        else if (getWidth() > 0 && getHeight() > 0) {
            isStarted = true;
            start();
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