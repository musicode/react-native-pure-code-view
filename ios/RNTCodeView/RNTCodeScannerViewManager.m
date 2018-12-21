
#import "RNTCodeScannerViewManager.h"
#import "RNTCodeView-Swift.h"
#import "RNTCodeScannerView.h"

@implementation RNTCodeScannerViewManager

RCT_EXPORT_MODULE()

- (UIView *)view {
    return [RNTCodeScannerView new];
}

RCT_CUSTOM_VIEW_PROPERTY(title, NSString, RNTCodeScannerView) {
    view.scanner.guideTitle = [RCTConvert NSString:json];
}

RCT_EXPORT_VIEW_PROPERTY(onScanSuccess, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onScanWithoutPermissions, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onPermissionsGranted, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onPermissionsDenied, RCTBubblingEventBlock);

@end
