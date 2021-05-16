import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_launch_store/flutter_launch_store.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final myController = TextEditingController();

  @override
  void dispose() {
    myController.dispose();
    super.dispose();
  }

  Future<void> openWithStore() async {
    var appId = myController.text;
    print('app id: $appId');
    try {
      StoreLauncher.openWithStore(appId).catchError((e) {
        print('ERROR> $e');
      });
    } catch (e) {
      print(e.toString());
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Plugin flutter_launch_store example app'),
        ),
        body: Container(
          padding: EdgeInsets.all(12.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextField(
                controller: myController,
                decoration: InputDecoration(
                  hintText: 'Please enter Package Name',
                  border: OutlineInputBorder(borderSide: BorderSide(color: Colors.teal)),
                ),
              ),
              ElevatedButton(
                onPressed: openWithStore,
                child: Text('Open With Store'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
