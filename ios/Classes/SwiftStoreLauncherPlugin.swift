import Flutter
import UIKit

public class SwiftStoreLauncherPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "flutter_launch_store", binaryMessenger: registrar.messenger())
        let instance = SwiftStoreLauncherPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        print("Call method: \(call.method)")
        if call.method == "getPlatformVersion" {
            result(getPlatformVersion())
        } else if call.method == "openWithStore" {
            guard let args = call.arguments else {
                result(FlutterError(code: "1", message: "Missing Parameter in method: (\(call.method))", details: nil))
                return
            }
            if let myArgs = args as? [String: Any],
            let appId = myArgs["app_id"] as? String {
                openWithAppStore(appId: appId) {(success) in
                    if success {
                        result("ok")
                    } else {
                        result(FlutterError(code: "-1", message: "Unknown Error in method: (\(call.method))", details: nil))
                    }
                }
            } else {
             result(FlutterError(code: "1", message: "Could not extract " +
             "flutter arguments in method: (\(call.method))", details: nil))
            }
        } else {
            result(FlutterMethodNotImplemented)
        }
    }
    
    private func getPlatformVersion() -> String {
        return "iOS " + UIDevice.current.systemVersion
    }
    
    private func openWithAppStore(appId: String, _ completion: ((Bool) -> ())?=nil) {
        if let url = URL(string: "itms-apps://itunes.apple.com/app/\(appId)"),
            UIApplication.shared.canOpenURL(url) {
            print(url)
            if #available(iOS 10.0, *) {
                UIApplication.shared.open(url, options: [:], completionHandler: completion)
            } else {
                let result = UIApplication.shared.openURL(url)
                completion?(result)
            }
        } else {
            completion?(false)
        }
    }
}
