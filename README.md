Android ARP Cache
======

Get Android ARP Cache or Query ARP Cache against an IP Address.

## Supported Platforms

- Android

## Installation

> cordova plugin add https://github.com/arvindr21/cordova-android-arp-cache.git

## Usage

This plugin defines a global `ARP` object.
Although the object is in the global scope, it is not available until after the `deviceready` event.

### Get Raw ARP Cache

> - success : Success callback
> - error : Error Callback


```js
ARP.getRawCache(function(resp) {
    console.log(resp);
    // Output
    /*
		"IP address       HW type     Flags       HW address            Mask     Device
		192.168.1.26     0x1         0x2         e4:02:9b:5a:d1:ba     *        wlan0
		192.168.1.11     0x1         0x2         08:11:96:9c:64:d0     *        wlan0
		192.168.1.3      0x1         0x2         dc:1a:c5:66:fa:a9     *        wlan0
		192.168.1.4      0x1         0x2         60:f8:1d:c9:1a:58     *        wlan0
		192.168.1.21     0x1         0x2         30:ae:a4:0e:12:f8     *        wlan0
		192.168.1.15     0x1         0x2         30:ae:a4:03:2f:04     *        wlan0
		192.168.1.7      0x1         0x2         50:8f:4c:8e:a9:27     *        wlan0
		192.168.1.8      0x1         0x0         24:0a:c4:81:cf:a4     *        wlan0
		192.168.1.16     0x1         0x2         30:ae:a4:0a:c1:ac     *        wlan0
		192.168.1.9      0x1         0x2         30:ae:a4:3f:43:18     *        wlan0
		192.168.1.1      0x1         0x2         10:62:eb:7d:4a:df     *        wlan0
		192.168.1.17     0x1         0x2         2c:6e:85:b2:12:f9     *        wlan0
		192.168.1.10     0x1         0x2         24:0a:c4:13:39:8c     *        wlan0"

    */
}, function(err) {
    console.log(err);
});
```

### Get Parsed ARP Cache

> - success : Success callback
> - error : Error Callback


```js
ARP.getParsedCache(function(resp) {
    console.log(resp);
    // Output
    /*
		[{
		    "ip": "192.168.1.26",
		    "mac": "e4:02:9b:5a:d1:ba"
		}, {
		    "ip": "192.168.1.11",
		    "mac": "08:11:96:9c:64:d0"
		}, {
		    "ip": "192.168.1.3",
		    "mac": "dc:1a:c5:66:fa:a9"
		}, {
		    "ip": "192.168.1.4",
		    "mac": "60:f8:1d:c9:1a:58"
		}, {
		    "ip": "192.168.1.21",
		    "mac": "30:ae:a4:0e:12:f8"
		}, {
		    "ip": "192.168.1.15",
		    "mac": "30:ae:a4:03:2f:04"
		}, {
		    "ip": "192.168.1.7",
		    "mac": "50:8f:4c:8e:a9:27"
		}, {
		    "ip": "192.168.1.8",
		    "mac": "24:0a:c4:81:cf:a4"
		}, {
		    "ip": "192.168.1.16",
		    "mac": "30:ae:a4:0a:c1:ac"
		}, {
		    "ip": "192.168.1.9",
		    "mac": "30:ae:a4:3f:43:18"
		}, {
		    "ip": "192.168.1.1",
		    "mac": "10:62:eb:7d:4a:df"
		}, {
		    "ip": "192.168.1.17",
		    "mac": "2c:6e:85:b2:12:f9"
		}, {
		    "ip": "192.168.1.10",
		    "mac": "24:0a:c4:13:39:8c"
		}]

    */
}, function(err) {
    console.log(err);
});
```

### Get Macaddress from IP address - Query ARP cache

> - ip : IP address of the device
> - success : Success callback
> - error : Error Callback

```js
ARP.getMacFromIp('192.168.1.1', function(resp) {
    console.log(resp);
    // Output -> {"ip":"192.168.1.1","mac":"10:62:eb:7d:4a:df"}
}, function(err) {
    console.log(err);
});
```

### Get IP address from Macaddress - Query ARP cache

> - mac : Macaddress of the device
> - success : Success callback
> - error : Error Callback

```js
ARP.getIPFromMac('10:62:eb:7d:4a:df', function(resp) {
    console.log(resp);
    // Output -> {"ip":"192.168.1.1","mac":"10:62:eb:7d:4a:df"}
}, function(err) {
    console.log(err);
});
```