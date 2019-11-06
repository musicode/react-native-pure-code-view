
#import "RNTQRCodeViewManager.h"
#import "react_native_pure_code_view-Swift.h"

@implementation RNTQRCodeViewManager

RCT_EXPORT_MODULE()

- (UIView *)view {
    return [QRCodeView new];
}

RCT_CUSTOM_VIEW_PROPERTY(text, NSString, QRCodeView) {
    view.text = [RCTConvert NSString:json];
}

@end
