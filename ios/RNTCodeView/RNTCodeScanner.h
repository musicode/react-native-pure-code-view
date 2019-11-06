
#import <React/RCTViewManager.h>

@interface RNTCodeScanner : UIView

@property (nonatomic, weak) UIView *scanner;

@property (nonatomic, copy) RCTBubblingEventBlock onScanSuccess;
@property (nonatomic, copy) RCTBubblingEventBlock onPermissionsNotGranted;
@property (nonatomic, copy) RCTBubblingEventBlock onPermissionsGranted;
@property (nonatomic, copy) RCTBubblingEventBlock onPermissionsDenied;

@end
