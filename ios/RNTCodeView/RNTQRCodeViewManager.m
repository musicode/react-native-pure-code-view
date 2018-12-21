
#import "RNTQRCodeViewManager.h"
#import "RNTCodeView-Swift.h"

@implementation RNTQRCodeViewManager

RCT_EXPORT_MODULE()

- (UIView *)view {
    QRCodeView *view = [QRCodeView new];
    return view;
}

RCT_CUSTOM_VIEW_PROPERTY(text, NSString, QRCodeView) {
    view.text = [RCTConvert NSString:json];
}

@end
