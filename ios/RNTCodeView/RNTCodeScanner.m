
#import "RNTCodeScanner.h"
#import "RNTCodeView-Swift.h"

@interface RNTCodeScanner()<CodeScannerDelegate>

@end

@implementation RNTCodeScanner

- (instancetype)init {
    self = [super init];
    if (self) {
        CodeScanner *view = [[CodeScanner alloc] initWithConfiguration:[CodeScannerConfiguration new] delegate:self];
        view.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
        [self addSubview:view];
        self.scanner = view;
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
