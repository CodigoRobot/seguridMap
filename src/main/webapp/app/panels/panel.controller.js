(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('PagePanelController', PagePanelController);

    PagePanelController.$inject = ['$scope', 'Principal', 'LoginService', '$state','olData','entity'];

    function PagePanelController ($scope, Principal, LoginService, $state,olData,entity) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.entity = entity;
        vm.register = register;

        olData.getMap("panelMap").then(function (map) {
            //Set default map
            var googleLayer = new olgm.layer.Google({
                title: 'Kart (Google)',
                type: 'base',
            });

            var wmsLayer = new ol.layer.Tile({
                title: 'Kart (Google) 1',
                source: new ol.source.TileWMS({
                    url: 'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/wms',
                    params: { 'LAYERS': 'mapaseguridad:regiones' },
                    serverType: 'geoserver',
                    //query: 'OBJECTID_1=262'
                    //cql_filter: 'OBJECTID_1=237'
                })
            });
            var view = new ol.View({
                center: ol.proj.fromLonLat([-104.7239094,20.8369366]),
                zoom: 7
            });

            map.setTarget(null);
                map = new ol.Map({
                interactions: olgm.interaction.defaults({mouseWheelZoom:false}),
                layers: [
                    googleLayer,
                    wmsLayer
                ],
                target: "panelMap",
                view: view
            });

            //map.addControl(new ol.control.LoadingPanel());
            map.on('singleclick', function (evt1) {
                console.log(evt1);

                var viewResolution = /** @type {number} */
                    (view.getResolution());


                if (wmsLayer.getVisible() ) {
                    var url = wmsLayer.getSource().getGetFeatureInfoUrl(evt1.coordinate, viewResolution, 'EPSG:3857', {
                        'INFO_FORMAT': 'application/json',
                        'FEATURE_COUNT': '300'
                    });
                    if (url) {
                        console.log(url);
                    }
                }

            });




            var layerSwitcher = new ol.control.LayerSwitcher({
                tipLabel: 'Leyenda'
            });

            map.addControl(layerSwitcher);
            layerSwitcher.showPanel();



            setTimeout(function(){
                var olGM = new olgm.OLGoogleMaps({map: map  });
                olGM.activate();
                map.updateSize();
            }, 2000);
        });




        $scope.$on('authenticationSuccess', function() {
            getAccount();

        });


        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
