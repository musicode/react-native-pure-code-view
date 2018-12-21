
import UIKit

public class BarCodeView: CodeView {
    
    override func createFilter() -> CIFilter? {
        
        guard let filter = CIFilter(name: "CICode128BarcodeGenerator") else {
            return nil
        }
        
        filter.setValue(0, forKey: "inputQuietSpace")
        
        return filter
        
    }
    
}
