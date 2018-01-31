Android ARP Cache
======

Get Android ARP Cache or Query ARP Cache against an IP Address or a MAC Address.

- Note: This plugin only retrieves the contents of the ARP cache file. The contents of the file are totally dependent on Android OS. More information: [ARP Man Page](https://linux.die.net/man/7/arp).

## Supported Platforms

- Android

## Installation
> cordova plugin add cordova-arp-cache-fetch

or

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
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "e4:02:9b:5a:d1:ba",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.11",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "30:ae:a4:3f:42:c0",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.3",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "60:f8:1d:c9:1a:58",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.4",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "50:8f:4c:8e:a9:27",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.6",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "2c:6e:85:6b:30:32",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.7",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "08:11:96:9c:64:d0",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.8",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "24:0a:c4:81:cf:a4",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.9",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "10:07:b6:37:dc:ef",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.1",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "10:62:eb:7d:4a:df",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.17",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "2c:6e:85:b2:12:f9",
		    "mask": "*",
		    "device": "wlan0"
		},
		{
		    "ip": "192.168.1.10",
		    "hwType": "0x1",
		    "flags": "0x2",
		    "mac": "30:ae:a4:0e:12:f8",
		    "mask": "*",
		    "device": "wlan0"
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

### Refresh ARP cache

> - success : Success callback
> - error : Error Callback

Note: This method will take greater than 254 * 300 ms (76.20 seconds) to complete.

```js
ARP.refreshCache(function(resp) {
    console.log(resp);
    // Output -> Report of the IP sweep
	/*
	{
	    "reachable": [
	    {
	        "ip": "192.168.1.1"
	    },
	    {
	        "ip": "192.168.1.5"
	    },
	    {
	        "ip": "192.168.1.8"
	    },
	    {
	        "ip": "192.168.1.10"
	    }],
	    "notReachable": [
	    {
	        "ip": "192.168.1.2"
	    },
	    {
	        "ip": "192.168.1.3"
	    },
	    {
	        "ip": "192.168.1.4"
	    },
	    {
	        "ip": "192.168.1.6"
	    },
	    {
	        "ip": "192.168.1.7"
	    },
	    .... SNIPP
	    ....
	    .... SNIPP
	    {
	        "ip": "192.168.1.253"
	    },
	    {
	        "ip": "192.168.1.254"
	    }]
	}

	*/
}, function(err) {
    console.log(err);
});