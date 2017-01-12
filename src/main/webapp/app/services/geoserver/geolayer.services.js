(function() {
    'use strict';
    angular
        .module('seguridMapApp')
        .factory('GeoLayers', GeoLayers);

    GeoLayers.$inject = ['Geoserver','$q'];

    function GeoLayers (Geoserver,$q) {
        var vm = {};

        vm.getLayerCenterByProperty = getLayerCenterByProperty;

        function getLayerCenterByProperty(workspace,layer,property,propertyValue) {
            return Geoserver.post('<?xml version="1.0" encoding="UTF-8"?>' +
                '<wps:Execute version="1.0.0" service="WPS" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.opengis.net/wps/1.0.0" xmlns:wfs="http://www.opengis.net/wfs" xmlns:wps="http://www.opengis.net/wps/1.0.0" xmlns:ows="http://www.opengis.net/ows/1.1" xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc" xmlns:wcs="http://www.opengis.net/wcs/1.1.1" xmlns:xlink="http://www.w3.org/1999/xlink" xsi:schemaLocation="http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsAll.xsd"> ' +
                '<ows:Identifier>vec:Bounds</ows:Identifier> ' +
                '<wps:DataInputs> ' +
                '<wps:Input> ' +
                '<ows:Identifier>features</ows:Identifier> ' +
                '<wps:Reference mimeType="text/xml" xlink:href="http://geoserver/wfs" method="POST"> ' +
                '<wps:Body> ' +
                '<wfs:GetFeature service="WFS" version="1.0.0" outputFormat="GML1" xmlns:'+workspace+'="'+workspace+'"> ' +
                '<wfs:Query typeName="'+layer+'">' +
                '<ogc:Filter>' +
                '<ogc:PropertyIsEqualTo>' +
                '<ogc:PropertyName>'+property+'</ogc:PropertyName>' +
                '<ogc:Literal>'+propertyValue+'</ogc:Literal>' +
                '</ogc:PropertyIsEqualTo>' +
                '</ogc:Filter>' +
                '</wfs:Query>' +
                '</wfs:GetFeature> ' +
                '</wps:Body> ' +
                '</wps:Reference> ' +
                '</wps:Input> ' +
                '</wps:DataInputs> ' +
                '<wps:ResponseForm> ' +
                '<wps:RawDataOutput> ' +
                '<ows:Identifier>bounds</ows:Identifier> ' +
                '</wps:RawDataOutput> ' +
                '</wps:ResponseForm> ' +
                '</wps:Execute>').$promise;
        }
        return vm;
    }
})();
