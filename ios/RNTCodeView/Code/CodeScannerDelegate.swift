
import Foundation

@objc public protocol CodeScannerDelegate {
    
    // 扫描成功时触发
    func codeScannerDidScanSuccess(_ codeScanner: CodeScanner, text: String)
    
}
