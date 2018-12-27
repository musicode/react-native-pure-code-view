
import UIKit
import AVFoundation

public class CodeScanner: UIView {

    @objc public var guideTitle = "" {
        didSet {
            guideLabel.text = guideTitle
            guideLabel.sizeToFit()
        }
    }
    
    private var supportedCodeTypes: [AVMetadataObject.ObjectType] = [ .qr, .code128 ]
    
    private var configuration: CodeScannerConfiguration!
    
    private var delegate: CodeScannerDelegate!

    private var captureSession: AVCaptureSession!
    
    private var captureDevice: AVCaptureDevice!
    
    private var capturePreviewLayer: AVCaptureVideoPreviewLayer?

    private var isTorchOn = false {
        didSet {
            if isTorchOn {
                if setTorchMode(.on) {
                    torchButton.setImage(configuration.torchOffImage, for: .normal)
                }
            }
            else {
                if setTorchMode(.off) {
                    torchButton.setImage(configuration.torchOnImage, for: .normal)
                }
            }
        }
    }
    
    private var isPreviewing = false {
        didSet {
            if isPreviewing {
                guideLabel.isHidden = false
                torchButton.isHidden = false
                laserView.isHidden = false
                startLaser()
            }
            else {
                guideLabel.isHidden = true
                torchButton.isHidden = true
                laserView.isHidden = true
                stopLaser()
            }
        }
    }
    
    private lazy var laserView: UIView = {
        
        let view = UIView()
        
        view.backgroundColor = configuration.laserColor
        view.isHidden = true
        
        addSubview(view)
        
        return view
        
    }()
    
    private lazy var viewFinder: ViewFinder = {
        
        let view = ViewFinder()
        
        view.maskColor = configuration.viewFinderMaskColor
        view.borderWidth = configuration.viewFinderBorderWidth
        view.borderColor = configuration.viewFinderBorderColor
        view.cornerSize = configuration.viewFinderCornerSize
        view.cornerWidth = configuration.viewFinderCornerWidth
        view.cornerColor = configuration.viewFinderCornerColor
        
        addSubview(view)
        
        return view
        
    }()
    
    private lazy var guideLabel: UILabel = {
        
        let view = UILabel()
        
        view.isHidden = true
        view.font = configuration.guideLabelTextFont
        view.textColor = configuration.guideLabelTextColor

        addSubview(view)
        
        return view
        
    }()
    
    private lazy var torchButton: UIButton = {
        
        let view = UIButton()
        
        view.setImage(configuration.torchOnImage, for: .normal)
        view.frame.size = CGSize(width: configuration.torchButtonWidth, height: configuration.torchButtonHeight)
        view.isHidden = true
        
        addSubview(view)
        
        view.addTarget(self, action: #selector(onTorchToggle), for: .touchUpInside)
        
        return view
        
    }()
    
    @objc public convenience init(configuration: CodeScannerConfiguration, delegate: CodeScannerDelegate) {
        self.init()
        self.configuration = configuration
        self.delegate = delegate
        setup()
    }

    private func setup() {
    
        backgroundColor = .clear

        updateView()
        
    }
    
    private func prepareDevice() {
        
        guard !isPreviewing else {
            return
        }
        
        guard let device = pickDevice() else {
            delegate.codeScannerWillScanWithoutPermissions(self)
            return
        }
        
        captureDevice = device
        
        captureSession = AVCaptureSession()
        
        do {
            try addInput(device: device)
        }
        catch {
            print(error.localizedDescription)
            return
        }
        
        addOutput()
        addPreview()
        
        captureSession.startRunning()
        
        isPreviewing = true
        
    }
    
    private func requestPermissions() {
        
        switch AVCaptureDevice.authorizationStatus(for: .video) {
        case .authorized:
            prepareDevice()
            break
        case .notDetermined:
            AVCaptureDevice.requestAccess(for: .video) { granted in
                DispatchQueue.main.async {
                    if granted {
                        self.prepareDevice()
                        self.delegate.codeScannerDidPermissionsGranted(self)
                    }
                    else {
                        self.delegate.codeScannerDidPermissionsDenied(self)
                    }
                }
            }
            break
        default:
            // 拒绝
            self.delegate.codeScannerWillScanWithoutPermissions(self)
            break
        }
        
    }
    
    private func pickDevice() -> AVCaptureDevice? {
        
        let devices: [AVCaptureDevice]

        if #available(iOS 10.0, *) {
            let session = AVCaptureDevice.DiscoverySession(deviceTypes: [.builtInWideAngleCamera], mediaType: .video, position: .unspecified)
            devices = session.devices
        }
        else {
            devices = AVCaptureDevice.devices(for: .video)
        }
        
        for device in devices {
            if device.position == .back {
                return device
            }
        }
        
        return nil
        
    }
    
    private func addInput(device: AVCaptureDevice) throws {
        
        let input = try AVCaptureDeviceInput(device: device)
        
        if captureSession.canAddInput(input) {
            captureSession.addInput(input)
        }
        
    }
    
    private func addOutput() {
        
        let output = AVCaptureMetadataOutput()
        
        if captureSession.canAddOutput(output) {
            captureSession.addOutput(output)
        }
        
        output.metadataObjectTypes = supportedCodeTypes
        output.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
        
    }
    
    private func addPreview() {
        
        if let capturePreviewLayer = capturePreviewLayer {
            capturePreviewLayer.removeFromSuperlayer()
        }
        
        let previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        previewLayer.videoGravity = .resizeAspectFill
        
        layer.insertSublayer(previewLayer, at: 0)
        
        capturePreviewLayer = previewLayer
        
    }
    
    private func setTorchMode(_ torchMode: AVCaptureDevice.TorchMode) -> Bool {
        
        do {
            try captureDevice.lockForConfiguration()
            
            captureDevice.torchMode = torchMode
            if torchMode == .on {
                try captureDevice.setTorchModeOn(level: 1.0)
            }
            
            captureDevice.unlockForConfiguration()
            
            return true
        }
        catch {
            print(error.localizedDescription)
        }
        
        return false
        
    }
    
    private func updateView() {

        var x: CGFloat = 0
        var y: CGFloat = 0
        
        var boxWidth: CGFloat = 0
        var boxHeight: CGFloat = 0
        
        if let capturePreviewLayer = capturePreviewLayer {
            
            capturePreviewLayer.frame = bounds
            
            let scale: CGFloat = 0.8
            
            // 确保是整型，否则小数会出现布局的细微偏移
            boxWidth = round(bounds.width * scale)
            boxHeight = round(bounds.height * scale)
            if boxHeight > boxWidth {
                boxHeight = boxWidth
            }
            
            x = round((bounds.width - boxWidth) / 2)
            y = round((bounds.height - boxHeight) / 2)
            
        }
        

        let box = CGRect(x: x, y: y, width: boxWidth, height: boxHeight)

        capturePreviewLayer?.frame = bounds
        
        // 这里的顺序必须是 laserView -> viewFinder -> 后面两个顺序无所谓
        laserView.frame.size = CGSize(width: boxWidth - 2 * configuration.viewFinderBorderWidth - 2 * configuration.laserGap, height: configuration.laserHeight)
        laserView.center.x = box.midX
        
        viewFinder.box = box
        viewFinder.frame = bounds
        viewFinder.setNeedsLayout()
        viewFinder.setNeedsDisplay()
        
        guideLabel.center.x = bounds.midX
        guideLabel.frame.origin.y = y + boxHeight + configuration.guideLabelMarginTop
        
        torchButton.center.x = bounds.midX
        torchButton.frame.origin.y = y - configuration.torchButtonMarginBottom - configuration.torchButtonHeight
        
        stopLaser()

    }

    private func startLaser() {

        guard let box = viewFinder.box, !laserView.isHidden else {
            return
        }
        
        let top = box.origin.y + configuration.viewFinderBorderWidth + configuration.laserHeight / 2
        let bottom = box.origin.y + box.height - configuration.viewFinderBorderWidth - configuration.laserHeight / 2
        
        laserView.center.y = top
        
        UIView.animate(withDuration: 3, delay: 0, options: .curveLinear, animations: {
            self.laserView.center.y = bottom
        }, completion: { success in
            self.startLaser()
        })
        
    }
    
    private func stopLaser() {
        laserView.layer.removeAllAnimations()
    }
    
    @objc private func onTorchToggle() {
        isTorchOn = !isTorchOn
    }
    
    public override func didMoveToWindow() {
        super.didMoveToWindow()
        requestPermissions()
    }
    
    public override func layoutSubviews() {
        super.layoutSubviews()
        updateView()
    }
    
}

extension CodeScanner: AVCaptureMetadataOutputObjectsDelegate {
    
    public func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        
        if metadataObjects.count == 0 {
            return
        }
        
        let metadataObject = metadataObjects[0]
        guard supportedCodeTypes.contains(metadataObject.type) else {
            return
        }
        
        let result = metadataObject as! AVMetadataMachineReadableCodeObject
        if let text = result.stringValue {
            delegate.codeScannerDidScanSuccess(self, text: text)
        }
        
    }
}
