
#import "RNTCodeScannerManager.h"
#import "react_native_pure_code_view-Swift.h"
#import "RNTCodeScanner.h"

@implementation RNTCodeScannerManager

RCT_EXPORT_MODULE()

- (UIView *)view {
    return [RNTCodeScanner new];
}

RCT_CUSTOM_VIEW_PROPERTY(title, NSString, RNTCodeScanner) {
    view.scanner.guideTitle = [RCTConvert NSString:json];
}

RCT_EXPORT_VIEW_PROPERTY(onScanSuccess, RCTBubblingEventBlock);

@end
