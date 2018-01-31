/**
 */
package com.arvindr21;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;

import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class ARP extends CordovaPlugin {
  private static final String TAG = "ARP";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }

  // https://stackoverflow.com/a/13007325/1015046
  public static String getLocalAddress(boolean useIPv4) {
    try {
      List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface intf : interfaces) {
        List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
        for (InetAddress addr : addrs) {
          if (!addr.isLoopbackAddress()) {
            String sAddr = addr.getHostAddress();
            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
            boolean isIPv4 = sAddr.indexOf(':') < 0;

            if (useIPv4) {
              if (isIPv4)
                return sAddr;
            } else {
              if (!isIPv4) {
                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
              }
            }
          }
        }
      }
    } catch (Exception ex) { } // for now eat exceptions
    return "";
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
        callbackContext.success(completeFile);
      } catch (Exception e) {
        callbackContext.error("Exception Occoured: " + e.toString());
      } finally {
        try {
          if (br != null) {
            br.close();
          }

        } catch (IOException e) {
          e.printStackTrace();
          callbackContext.error("Exception Occoured: " + e.toString());
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

        callbackContext.success(jsonArray.toString());
      } catch (Exception e) {
        callbackContext.error("Exception Occoured: " + e.toString());
      } finally {
        try {
          if (br != null) {
            br.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
          callbackContext.error("Exception Occoured: " + e.toString());
        }
      }
    } else if (action.equals("getMacFromIp")) {
      String ip = args.getString(0);
      if (ip == null) {
        callbackContext.error("NO IP ADDRESS PROVIDED");
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
                callbackContext.success(item.toString());
              } else {
                callbackContext.error("NO RESULTS FOUND");
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          callbackContext.error("Exception Occoured: " + e.toString());
        } finally {
          try {
            if (br != null) {
              br.close();
            }

          } catch (IOException e) {
            e.printStackTrace();
            callbackContext.error("Exception Occoured: " + e.toString());
          }
        }
      }
    } else if (action.equals("getIPFromMac")) {
      String mac = args.getString(0);
      if (mac == null) {
        callbackContext.error("NO MACADDRESS PROVIDED");
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
              callbackContext.success(item.toString());
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          callbackContext.error("Exception Occoured: " + e.toString());
        } finally {
          try {
            if (br != null) {
              br.close();
            }

          } catch (IOException e) {
            e.printStackTrace();
            callbackContext.error("Exception Occoured: " + e.toString());
          }
        }
      }
    } else if (action.equals("refreshCache")) {
      cordova.getThreadPool().execute(new Runnable() {
        public void run() {
          JSONArray jsonArray = new JSONArray();
          try {
            String ipString = getLocalAddress(true); // IPV4

            if (ipString == null) {
              callbackContext.error("NO IP ADDRESS");
            }

            InetAddress ipObj = InetAddress.getByName(ipString);
            byte[] ip = ipObj.getAddress();

            for (int i = 1; i <= 254; i++) {

              ip[3] = (byte)i;
              InetAddress address = InetAddress.getByAddress(ip);

              if (address.isReachable(100)) {
                String output = address.toString().substring(1);
                JSONObject item = new JSONObject();
                item.put("ip", address.getHostAddress());
                jsonArray.put(item);
              }
            }

            callbackContext.success(jsonArray.toString());
          } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error("Exception Occoured: " + e.toString());
          }
        }
      });

      return true;
    }
    return false; //method not found :/
  }
}
