(function() {
    'use strict';
    angular
        .module('seguridMapApp')
        .factory('Geoserver', Geoserver);

    Geoserver.$inject = ['$resource'];

    function Geoserver ($resource) {
        var geo =  'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/wms';

        var geoService = $resource(geo, {}, {
            'query': { method: 'GET',
                transformResponse: function (data,header) {
                    var contentType = header('Content-Type');
                    if(contentType.indexOf('json') !== -1){
                        var jsonData = angular.fromJson(data);
                        return jsonData;
                    }
                    return {xml:data};
                }
            },
            'post': { method: 'POST',
                transformResponse: function (data) {
                    var x2js = new X2JS();
                    var jsonResponse = x2js.xml_str2json(data);
                    return jsonResponse;
                }
            },
        });

        return geoService;
    }
})();
