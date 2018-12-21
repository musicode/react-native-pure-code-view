
import UIKit
import Foundation

@objc public class CodeScannerConfiguration: NSObject {
    
    public var viewFinderMaskColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.61)
    
    public var viewFinderCornerColor = UIColor(red: 1, green: 0.48, blue: 0.03, alpha: 1)
    
    public var viewFinderCornerWidth: CGFloat = 2
    
    public var viewFinderCornerSize: CGFloat = 16
    
    public var viewFinderBorderWidth = 1 / UIScreen.main.scale
    
    public var viewFinderBorderColor = UIColor(red: 255, green: 255, blue: 255, alpha: 0.6)
    
    public var guideLabelTextFont = UIFont.systemFont(ofSize: 12)
    
    public var guideLabelTextColor = UIColor(red: 255, green: 255, blue: 255, alpha: 0.7)
    
    public var guideLabelMarginTop: CGFloat = 14
    
    public var laserGap: CGFloat = 10
    
    public var laserHeight: CGFloat = 1
    
    public var laserColor = UIColor(red: 1, green: 0.48, blue: 0.03, alpha: 0.625)
    
    public var torchOnImage = UIImage(named: "code_scanner_torch_on")
    
    public var torchOffImage = UIImage(named: "code_scanner_torch_off")
    
    public var torchButtonWidth: CGFloat = 44
    
    public var torchButtonHeight: CGFloat = 44
    
    public var torchButtonMarginBottom: CGFloat = 0

}
