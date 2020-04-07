
#import <React/RCTViewManager.h>

@class CodeScanner;

@interface RNTCodeScanner : UIView

@property (nonatomic, weak) CodeScanner *scanner;

@property (nonatomic, copy) RCTBubblingEventBlock onScanSuccess;

@end
