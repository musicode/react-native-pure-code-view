
#import "RNTCodeScannerView.h"
#import "RNTCodeView-Swift.h"

@interface RNTCodeScannerView()<CodeScannerDelegate>

@end

@implementation RNTCodeScannerView

- (instancetype)init {
    self = [super init];
    if (self) {
        CodeScanner *scanner = [CodeScanner new];
        
        [scanner initWithConfiguration:[CodeScannerConfiguration new] delegate:self];
        
        [self addSubview:scanner];
        
        scanner.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
        
        _scanner = scanner;
    }
    return self;
}

- (void)codeScannerDidScanSuccess:(CodeScanner *)codeScanner text:(NSString *)text {
    self.onScanSuccess(@{
                         @"text": text
                         });
}

- (void)codeScannerWillScanWithoutPermissions:(CodeScanner *)codeScanner {
    self.onScanWithoutPermissions(@{});
}

- (void)codeScannerDidPermissionsGranted:(CodeScanner *)codeScanner {
    self.onPermissionsGranted(@{});
}

- (void)codeScannerDidPermissionsDenied:(CodeScanner *)codeScanner {
    self.onPermissionsDenied(@{});
}

@end
