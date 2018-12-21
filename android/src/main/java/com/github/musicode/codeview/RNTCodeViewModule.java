
package com.github.musicode.codeview;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class RNTCodeViewModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNTCodeViewModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNTChatView";
  }

}