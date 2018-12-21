
#import <React/RCTViewManager.h>
#import "RNTCodeView-Swift.h"

@interface RNTCodeScannerView : UIView

@property (nonatomic, weak) CodeScanner *scanner;

@property (nonatomic, copy) RCTBubblingEventBlock onScanSuccess;
@property (nonatomic, copy) RCTBubblingEventBlock onScanWithoutPermissions;
@property (nonatomic, copy) RCTBubblingEventBlock onPermissionsGranted;
@property (nonatomic, copy) RCTBubblingEventBlock onPermissionsDenied;

@end
