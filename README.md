# flutter_launch_store

A Flutter plugin for launching a Store in the mobile platform. Supports AppStore, PlayStore and AppGallery.

It's a fork of the original https://github.com/heqingbao/store_launcher that seemed to be abandoned.

## Usage

To use this plugin, add `flutter_launch_store` as a [dependency in your pubspec.yaml file](https://flutter.dev/platform-plugins/).

### Example

``` dart
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
