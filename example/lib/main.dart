import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:barometer/barometer.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  //String _platformVersion = 'Unknown';
  double _reading;
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    //String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      var ready = await Barometer.initialize();
      print(ready); 
    } on PlatformException {
    
     // platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
     // _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
             Text('Last reading: $_reading\n', style: Theme.of(context).textTheme.headline,),
             RaisedButton(
               child: Text('Get pressure',),
               onPressed:() async{
                 final reading = await Barometer.reading;
                 setState(() => _reading = reading);
                 print(reading);
               },
             ),

             /* RaisedButton(
                child: Text('Get last pressure'),
                onPressed: () async{
                  final reading = await Barometer.reading;
                  setState(() => _reading = reading);
                },
              ),*/
            ],
          ),
        ),
      ),
    );
  }
}
