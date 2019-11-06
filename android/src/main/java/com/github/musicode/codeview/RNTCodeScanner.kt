package com.github.musicode.codeview

import android.view.Choreographer

import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.uimanager.ThemedReactContext
import com.github.herokotlin.code.CodeScanner

class RNTCodeScanner(reactContext: ThemedReactContext) : CodeScanner(reactContext), LifecycleEventListener {

    private var isStarted = false

    private var isResuming = false

    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child.measure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY))
                child.layout(0, 0, child.measuredWidth, child.measuredHeight)
            }
            viewTreeObserver.dispatchOnGlobalLayout()
            Choreographer.getInstance().postFrameCallback(this)
        }
    }

    init {
        activity = reactContext.currentActivity
        reactContext.addLifecycleEventListener(this)
        Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    fun destroy() {
        stop()
        (context as ThemedReactContext).removeLifecycleEventListener(this)
        Choreographer.getInstance().removeFrameCallback(frameCallback)
        activity = null
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0 && isResuming && !isStarted) {
            isStarted = true
            start()
        }
    }

    override fun onHostResume() {
        isResuming = true
        if (isStarted) {
            resume()
        } else if (width > 0 && height > 0) {
            isStarted = true
            start()
        }
    }

    override fun onHostPause() {
        isResuming = false
        pause()
    }

    override fun onHostDestroy() {

    }

}