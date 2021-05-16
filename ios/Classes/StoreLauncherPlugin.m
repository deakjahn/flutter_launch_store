#import "StoreLauncherPlugin.h"
#if __has_include(<flutter_launch_store/flutter_launch_store-Swift.h>)
#import <flutter_launch_store/flutter_launch_store-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_launch_store-Swift.h"
#endif

@implementation StoreLauncherPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftStoreLauncherPlugin registerWithRegistrar:registrar];
}
@end
