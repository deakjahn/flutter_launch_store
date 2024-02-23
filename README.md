# flutter_launch_store

A Flutter plugin for launching a Store in the mobile platform. Supports AppStore, PlayStore and AppGallery.

On Android, it checks whether the appropriate vendor service is installed. If both Google's and Huawei's
services are present, it launches Google Play unless the app package ID ends in ".huawei".

It's a fork of the original https://github.com/heqingbao/store_launcher that seemed to be abandoned.

## Usage

To use this plugin, add `flutter_launch_store` as a [dependency in your pubspec.yaml file](https://flutter.dev/platform-plugins/).

Note that the Android Emulator doesn't normally have the stores installed. You should try the functioning on a real device.

### Example

```dart
import 'package:flutter_launch_store/flutter_launch_store.dart';

try {
  final appId = 'com.example.yourapppackageid';
  StoreLauncher.openWithStore(appId).catchError((e) {
    print('ERROR> $e');
  });
} catch (e) {
  print(e.toString());
}
```

### iOS

Don't forget to add the following to `Info.plist`:

```xml
<key>LSApplicationQueriesSchemes</key>
<array>
    <string>itms-apps</string>
</array>
```
