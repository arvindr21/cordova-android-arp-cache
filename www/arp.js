var exec = require('cordova/exec');

var PLUGIN_NAME = 'ARP';

var ARP = {
    getCache: function(success, error) {
        exec(success, error, PLUGIN_NAME, 'getCache');
    }
};

module.exports = ARP;