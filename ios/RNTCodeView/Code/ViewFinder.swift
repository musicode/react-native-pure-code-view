
import UIKit

public class ViewFinder: UIView {
    
    public var maskColor: UIColor!
    
    public var cornerColor: UIColor!
    
    public var cornerWidth: CGFloat!
    
    public var cornerSize: CGFloat!
    
    public var borderWidth: CGFloat!
    
    public var borderColor: UIColor!
    
    public var box: CGRect!

    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = .clear
    }
    
    public required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    public override func draw(_ rect: CGRect) {
        
        guard let context = UIGraphicsGetCurrentContext(), box.width > 0, box.height > 0 else {
            return
        }
        
        // 遮罩
        context.setFillColor(maskColor.cgColor)
        context.addRect(bounds)
        context.drawPath(using: .fill)
        context.clear(box)
        
        let left = box.origin.x
        let top = box.origin.y
        let right = left + box.width
        let bottom = top + box.height
        
        // 边框
        if borderWidth > 0 {
            let halfBorderWidth = borderWidth / 2
            context.setStrokeColor(borderColor.cgColor)
            context.setLineWidth(borderWidth)
            context.addRect(CGRect(x: left + halfBorderWidth, y: top + halfBorderWidth, width: box.width - borderWidth, height: box.height - borderWidth))
            context.drawPath(using: .stroke)
        }
        
        
        context.setFillColor(cornerColor.cgColor)
        
        // 左上
        context.addRect(CGRect(x: left, y: top, width: cornerSize, height: cornerWidth))
        context.addRect(CGRect(x: left, y: top + cornerWidth, width: cornerWidth, height: cornerSize - cornerWidth))
        
        // 右上
        context.addRect(CGRect(x: right - cornerSize, y: top, width: cornerSize, height: cornerWidth))
        context.addRect(CGRect(x: right - cornerWidth, y: top + cornerWidth, width: cornerWidth, height: cornerSize - cornerWidth))
        
        // 右下
        context.addRect(CGRect(x: right - cornerWidth, y: bottom - cornerSize, width: cornerWidth, height: cornerSize))
        context.addRect(CGRect(x: right - cornerSize, y: bottom - cornerWidth, width: cornerSize, height: cornerWidth))

        // 左下
        context.addRect(CGRect(x: left, y: bottom - cornerSize, width: cornerWidth, height: cornerSize))
        context.addRect(CGRect(x: left + cornerWidth, y: bottom - cornerWidth, width: cornerSize - cornerWidth, height: cornerWidth))
        
        context.drawPath(using: .fill)
        
    }
    
}
