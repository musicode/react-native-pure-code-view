
#import "RNTBarCodeViewManager.h"
#import "RNTCodeView-Swift.h"

@implementation RNTBarCodeViewManager

RCT_EXPORT_MODULE()

- (UIView *)view {
    return [BarCodeView new];
}

RCT_CUSTOM_VIEW_PROPERTY(text, NSString, BarCodeView) {
    view.text = [RCTConvert NSString:json];
}

@end
