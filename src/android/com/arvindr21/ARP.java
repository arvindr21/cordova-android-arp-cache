/**
 */
package com.arvindr21;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import java.util.Date;

import java.io.BufferedReader;

public class ARP extends CordovaPlugin {
  private static final String TAG = "ARP";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing ARP");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if(action.equals("getCache")) {
      BufferedReader br = null;
      br = new BufferedReader(new FileReader("/proc/net/arp"));
      String line; String completeString = "";
      while ((line = br.readLine()) != null) {
        completeString += line;
      }
      final PluginResult result = new PluginResult(PluginResult.Status.OK, completeString);
      callbackContext.sendPluginResult(result);
    } 
    return true;
  }

}
