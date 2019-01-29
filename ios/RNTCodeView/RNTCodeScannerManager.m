
#import "RNTCodeScannerManager.h"
#import "RNTCodeView-Swift.h"
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
RCT_EXPORT_VIEW_PROPERTY(onScanWithoutPermissions, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onPermissionsGranted, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onPermissionsDenied, RCTBubblingEventBlock);

@end
