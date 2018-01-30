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
import java.io.IOException;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ARP extends CordovaPlugin {
  private static final String TAG = "ARP";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing ARP");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (action.equals("getRawCache")) {
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader("/proc/net/arp"));
        String line; String completeFile = "";
        while ((line = br.readLine()) != null) {
          completeFile += line;
        }
        final PluginResult result = new PluginResult(PluginResult.Status.OK, completeFile);
        callbackContext.sendPluginResult(result);
      } catch (Exception e) {
        final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "Error!!");
        callbackContext.sendPluginResult(result);
      } finally {
        try {
          if (br != null) {
            br.close();
          }

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } if (action.equals("getParsedCache")) {
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader("/proc/net/arp"));
        String line;
        JSONArray jsonArray = new JSONArray();
        while ((line = br.readLine()) != null) {
          String[] splitted = line.split(" +");
          if (splitted != null && splitted.length >= 4) {
            String ip = splitted[0];
            String mac = splitted[3];
            if (mac.matches("..:..:..:..:..:..")) {
              JSONObject item = new JSONObject();
              item.put("ip", ip);
              item.put("mac", mac);
              jsonArray.put(item);
            }
          }
        }


        final PluginResult result = new PluginResult(PluginResult.Status.OK, jsonArray.toString());
        callbackContext.sendPluginResult(result);
      } catch (Exception e) {
        final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "Error!!");
        callbackContext.sendPluginResult(result);
      } finally {
        try {
          if (br != null) {
            br.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else if (action.equals("getMacFromIp")) {
      String ip = args.getString(0);
      if (ip == null) {
        final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "NO IP PROVIDED");
        callbackContext.sendPluginResult(result);
      } else {
        BufferedReader br = null;
        try {
          br = new BufferedReader(new FileReader("/proc/net/arp"));
          String line;
          while ((line = br.readLine()) != null) {
            String[] splitted = line.split(" +");
            if (splitted != null && splitted.length >= 4 && ip.equals(splitted[0])) {
              String mac = splitted[3];
              if (mac.matches("..:..:..:..:..:..")) {
                JSONObject item = new JSONObject();
                item.put("ip", ip);
                item.put("mac", mac);
                final PluginResult result = new PluginResult(PluginResult.Status.OK, item.toString());
                callbackContext.sendPluginResult(result);
              } else {
                final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "NO RESULT FOUND");
                callbackContext.sendPluginResult(result);
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (br != null) {
              br.close();
            }

          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    } else if (action.equals("getIPFromMac")) {
      String mac = args.getString(0);
      if (mac == null) {
        final PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "NO MAC PROVIDED");
        callbackContext.sendPluginResult(result);
      } else {
        BufferedReader br = null;
        try {
          br = new BufferedReader(new FileReader("/proc/net/arp"));
          String line;
          while ((line = br.readLine()) != null) {
            String[] splitted = line.split(" +");
            if (splitted != null && splitted.length >= 4 && mac.equals(splitted[3])) {
              String ip = splitted[0];
                JSONObject item = new JSONObject();
                item.put("ip", ip);
                item.put("mac", mac);
                final PluginResult result = new PluginResult(PluginResult.Status.OK, item.toString());
                callbackContext.sendPluginResult(result);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (br != null) {
              br.close();
            }

          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return true;
  }
}
