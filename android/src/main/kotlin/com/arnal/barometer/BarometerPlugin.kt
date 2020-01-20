package com.arnal.barometer

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** BarometerPlugin */
public class BarometerPlugin(var registrar: Registrar) : MethodCallHandler, SensorEventListener{

  private var mSensorManager: SensorManager? = null
  private var mBarometer: Sensor? = null
  private var mLatestReading: Float = 0.0F


  private fun initializeBarometer(): Boolean{
    mSensorManager = registrar.activeContext().getSystemService(SENSOR_SERVICE) as SensorManager?
    mBarometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_PRESSURE)
    mSensorManager?.registerListener(this, mBarometer, SensorManager.SENSOR_DELAY_NORMAL)
    return true
  }

  private fun  getBarometer(): Float{
    return mLatestReading
  }
 /* override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "barometer")
    channel.setMethodCallHandler(BarometerPlugin(registrar));
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.*/
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "barometer")
      channel.setMethodCallHandler(BarometerPlugin(registrar))
    }


  }



  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {

    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    }


    if (call.method == "getBarometer") {
        val reading = getBarometer()
        result.success(reading)
        return
    }

    if (call.method == "initializeBarometer") {
      result.success(initializeBarometer())
      return
    }



      result.notImplemented()

  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
   // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onSensorChanged(event: SensorEvent?) {
    mLatestReading = event?.values?.get(0)!!
      Log.d("Sensor", event.values.toString())
  }


}
