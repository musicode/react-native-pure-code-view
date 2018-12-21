
import UIKit


import UIKit

public class QRCodeView: CodeView {
    
    override func createFilter() -> CIFilter? {
        
        guard let filter = CIFilter(name: "CIQRCodeGenerator") else {
            return nil
        }
        
        filter.setValue("H", forKey: "inputCorrectionLevel")
        
        return filter
        
    }
    
}

