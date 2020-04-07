
#import "RNTCodeScanner.h"
#import "react_native_pure_code_view-Swift.h"

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

@end
