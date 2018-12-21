
import Foundation

@objc public protocol CodeScannerDelegate {
    
    // 扫描时，发现没权限
    func codeScannerWillScanWithoutPermissions(_ codeScanner: CodeScanner)
    
    // 扫描成功时触发
    func codeScannerDidScanSuccess(_ codeScanner: CodeScanner, text: String)
    
    // 用户点击同意授权
    func codeScannerDidPermissionsGranted(_ codeScanner: CodeScanner)
    
    // 用户点击拒绝授权
    func codeScannerDidPermissionsDenied(_ codeScanner: CodeScanner)
    
}

public extension CodeScannerDelegate {
    
    func codeScannerWillScanWithoutPermissions(_ codeScanner: CodeScanner) { }
    
    func codeScannerDidScanSuccess(_ codeScanner: CodeScanner, text: String) { }
    
    func codeScannerDidPermissionsGranted(_ codeScanner: CodeScanner) { }
    
    func codeScannerDidPermissionsDenied(_ codeScanner: CodeScanner) { }
    
}

