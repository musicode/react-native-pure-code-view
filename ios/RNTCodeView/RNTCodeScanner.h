
#import <React/RCTViewManager.h>

@class CodeScanner;

@interface RNTCodeScanner : UIView

@property (nonatomic, weak) CodeScanner *scanner;

@property (nonatomic, copy) RCTBubblingEventBlock onScanSuccess;
@property (nonatomic, copy) RCTBubblingEventBlock onPermissionsNotGranted;
@property (nonatomic, copy) RCTBubblingEventBlock onPermissionsGranted;
@property (nonatomic, copy) RCTBubblingEventBlock onPermissionsDenied;

@end
