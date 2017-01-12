(function() {
    'use strict';
    angular
        .module('seguridMapApp')
        .factory('Home', Home);

    Home.$inject = ['$resource'];

    function Home ($resource) {
        var resourceRegiones =  'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/wms?service=WFS' +
            '&version=1.0.0' +
            '&request=GetFeature' +
            '&typename=mapaseguridad%3Aregiones' +
            '&PROPERTYNAME=REGION,OID' +
            '&outputFormat=application/json';

        var regiones = $resource(resourceRegiones, {}, {
            'getAll': { method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
        });

        var resourceRegionesGeom =  'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/wms?service=WFS' +
            '&version=1.0.0' +
            '&request=GetFeature' +
            '&typename=mapaseguridad%3Aregiones' +
            '&PROPERTYNAME=the_geom' +
            '&CQL_FILTER=OID%3D:oid' +
            '&SRSname=EPSG:4326' +
            '&outputFormat=application/json';

        var regionesGeom = $resource(resourceRegionesGeom, {}, {
            'getRegionGeom': { method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
        });
        var resources = {};
        resources.regiones = regiones;
        resources.regionesGeom = regionesGeom;

        return resources;
    }
})();
/**
 * Created by jmruvalcaba on 07/01/17.
 */
