package io.flutter.plugins.flutter_launch_store

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.content.pm.PackageManager

class StoreLauncherPlugin : FlutterPlugin, MethodCallHandler {
  private lateinit var context: Context

  override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    val channel = MethodChannel(binding.binaryMessenger, "flutter_launch_store")
    context = binding.applicationContext
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, @NonNull result: Result) {
    if (call.method == "openWithStore") {
      if (!call.hasArgument("app_id")) {
        result.error("1", "Missing Parameter in method: (${call.method})", null)
        return
      }
      val packageName: String = call.argument("app_id")!!
      if (openWithStore(packageName)) {
        result.success("ok")
      }
      else {
        result.error("1", "Unknown Error in method: (${call.method})", null)
      }
    }
    else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
  }

  private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
    return try {
      packageManager.getApplicationInfo(packageName, 0).enabled
    }
    catch (e: PackageManager.NameNotFoundException) {
      false
    }
  }

  private fun launchIntent(url: String, packageName: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW).apply {
      data = Uri.parse(url)
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
      addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
      setPackage(packageName)
    }
    context.startActivity(intent)
    return true
  }

  private fun launchWeb(url: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (intent.resolveActivity(context.packageManager) == null) {
      return false
    }
    else {
      context.startActivity(intent)
      return true
    }
  }

  private fun openWithStore(appId: String): Boolean {
    if (appId.endsWith(".huawei", true)) {
      return launchIntent("appmarket://details?id=$appId", "com.huawei.appmarket")
    }
    else if (isPackageInstalled("com.android.vending", context.packageManager)) {
      return launchIntent("market://details?id=$appId", "com.android.vending")
    }
    else if (isPackageInstalled("com.huawei.appmarket", context.packageManager)) {
      return launchIntent("appmarket://details?id=$appId", "com.huawei.appmarket")
    }
    else {
      return launchWeb("https://play.google.com/store/apps/details?id=$appId")
    }
  }
}