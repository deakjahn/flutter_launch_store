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
import io.flutter.plugin.common.PluginRegistry.Registrar
import android.content.pm.PackageManager

/** StoreLauncherPlugin */
class StoreLauncherPlugin : FlutterPlugin, MethodCallHandler {

  private lateinit var context: Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_launch_store")
    context = flutterPluginBinding.applicationContext
    channel.setMethodCallHandler(this)
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "flutter_launch_store")
      channel.setMethodCallHandler(StoreLauncherPlugin())
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
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

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
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
    if (appId.endsWith('.huawei', true)) {
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
