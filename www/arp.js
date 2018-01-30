var exec = require('cordova/exec');

var PLUGIN_NAME = 'ARP';

var ARP = {
    getRawCache: function(success, error) {
        exec(success, error, PLUGIN_NAME, 'getRawCache');
    },
    getParsedCache: function(success, error) {
        exec(function(data) {
            success(JSON.parse(data));
        }, error, PLUGIN_NAME, 'getParsedCache');
    },
    getMacFromIp: function(IP, success, error) {
        exec(function(data) {
            success(JSON.parse(data));
        }, error, PLUGIN_NAME, 'getMacFromIp', [IP]);
    },
    getIPFromMac: function(Mac, success, error) {
        exec(function(data) {
            success(JSON.parse(data));
        }, error, PLUGIN_NAME, 'getIPFromMac', [Mac]);
    }

};

module.exports = ARP;