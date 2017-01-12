(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state','olData','Home','UserReport','Geoserver','GeoLayers'];

    function HomeController ($scope, Principal, LoginService, $state,olData,Home,UserReport,Geoserver,GeoLayers) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.itemsPerPage = 3;
        vm.login = LoginService.open;
        vm.register = register;
        vm.filters = {};
        vm.catalogs = {};
        vm.blocks = {};
        vm.catalogs.regiones = Home.regiones.getAll();

        vm.cql_filter_region = "INCLUDE";
        vm.cql_filter_mun = "INCLUDE";

        //Initialize filters hierarchy
        vm.cql = {
            region : {
                lr : ['INCLUDE'],
                hm : ['INCLUDE']
            },
            municipios: {
                lm : ['INCLUDE']
            },
            colonias: {
                lc : ['INCLUDE']
            }
        };


        var map = {};
        /**
         * Layers
         */
        var googleLayer = new olgm.layer.Google({
            title: 'Google',
            type: 'base',
        }); //-
        var jaliscoContorno = new ol.layer.Tile({
            title: 'Poligono Jalisco',
            source: new ol.source.TileWMS({
                url: 'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/gwc/service/wms',
                params: {
                    'LAYERS' : 'mapaseguridad:jalisco',
                    'tiled' : true
                },
                serverType: 'geoserver',
            })
        }); //-
        var regiones = new ol.layer.Tile({
            title: 'Regiones',
            source: new ol.source.TileWMS({
                url: 'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/gwc/service/wms',
                params: {
                    'LAYERS' : 'mapaseguridad:regiones',
                    'cql_filter' : vm.cql.region.lr.slice(-1),
                    'tiled' : true
                },
                serverType: 'geoserver',
            })
        }); //-
        var municipios = new ol.layer.Tile({
            title: 'Municipios',
            source: new ol.source.TileWMS({
                url: 'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/gwc/service/wms',
                params: {
                    'LAYERS' : 'mapaseguridad:municipios',
                    'cql_filter' : vm.cql.municipios.lm.slice(-1),
                    'tiled' : true
                },
                serverType: 'geoserver',
            })
        }); //-
            municipios.setVisible(false);
        var colonias = new ol.layer.Tile({
            title: 'Colonias',
            source: new ol.source.TileWMS({
                url: 'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/gwc/service/wms',
                params: {
                    'LAYERS' : 'mapaseguridad:colonias',
                    'cql_filter' : vm.cql.colonias.lc.slice(-1),
                    'tiled' : true
                },
                serverType: 'geoserver',
            })
        }); //-
        colonias.setVisible(false);


        var heatMapDelitos = new ol.layer.Tile({
            title: 'Mapa de calor delitos',
            source: new ol.source.TileWMS({
                url: 'https://geo.coderobot.com.mx:8443/geoserver/mapaseguridad/gwc/service/wms',
                params: {
                    'LAYERS': 'mapaseguridad:user_report_heat',
                    cql_filter : vm.cql.region.hm.slice(-1),
                    'tiled' : true
                },
                serverType: 'geoserver',
            })
        }); //-


        var source = new ol.source.Vector({wrapX: false});
        var vector = new ol.layer.Vector({
            source: source
        });
        /**
         * Views
         */
        var view = new ol.View({
            center: ol.proj.fromLonLat([-103.7251757,20.8369366]),
            zoom: 7
        });
        /**
         * Controls
         */
        var layerSwitcher = new ol.control.LayerSwitcher({
            tipLabel: 'Leyenda'
        });

        /**
         * Map configuration
         */
        olData.getMap("main1").then(function (oMap) {
            oMap.setTarget(null);

            map = new ol.Map({
                interactions: olgm.interaction.defaults({mouseWheelZoom:false}),
                layers: [
                    googleLayer,
                    jaliscoContorno,
                    regiones,
                    municipios,
                    colonias,
                    vector,
                    heatMapDelitos

                ],
                target: "main1",
                view: view
            });
            //Init base layer
            setTimeout(function(){
                var olGM = new olgm.OLGoogleMaps({map: map  });
                olGM.activate();
                map.updateSize();
            }, 2000);
            map.updateSize();

            //Add draw filters
            var drawPolygon = new ol.interaction.Draw({
                source: source,
                type: /** @type {ol.geom.GeometryType} */ 'Polygon'
            });
            drawPolygon.on('drawstart', function(evt){
                source.clear();
            });
            drawPolygon.on('drawend', function(evt){
                setTimeout(function(){
                    source.clear();
                    map.removeInteraction(drawPolygon);
                }, 100);
            });

            var drawPolygonButton = document.createElement('button');
            drawPolygonButton.innerHTML = 'N';
            drawPolygonButton.addEventListener('click', function(e) {
                if(!map.removeInteraction(drawPolygon)){
                    map.addInteraction(drawPolygon);
                }
            }, false);

            var drawPolygonElement = document.createElement('div');
            drawPolygonElement.className = 'draw-polygon ol-unselectable ol-control';
            drawPolygonElement.appendChild(drawPolygonButton);

            var drawCircle = new ol.interaction.Draw({
                source: source,
                type: /** @type {ol.geom.GeometryType} */ 'Circle'
            });
            drawCircle.on('drawstart', function(evt){
                source.clear();
            });
            drawCircle.on('drawend', function(evt){
                setTimeout(function(){
                    source.clear();
                    map.removeInteraction(drawCircle);
                }, 100);
            });

            var drawCircleButton = document.createElement('button');
                drawCircleButton.innerHTML = 'N';
                drawCircleButton.addEventListener('click', function(e) {
                    if(!map.removeInteraction(drawCircle)){
                        map.addInteraction(drawCircle);
                    }
                }, false);

            var drawCircleElement = document.createElement('div');
                drawCircleElement.className = 'draw-circle ol-unselectable ol-control';
                drawCircleElement.appendChild(drawCircleButton);

            /**
             * Add controls
             */
            map.addControl(layerSwitcher); layerSwitcher.showPanel();

            map.addControl(new ol.control.Control({
                element: drawPolygonElement
            }));

            map.addControl(new ol.control.Control({
                element: drawCircleElement
            }));
        });

        //Update heatMap & region by region field
        $scope.$watch('vm.filters.region', function(newRegion, oldRegion) {
            if(newRegion!=oldRegion){
                if(newRegion != undefined) {
                    vm.cql.region.hm.push("INTERSECTS(position, querySingle('regiones', 'the_geom','OID = " + newRegion.properties.OID + "'))");
                    vm.cql.region.lr.push("OID = " + newRegion.properties.OID);
                    //Center map
                    GeoLayers.getLayerCenterByProperty('mapaseguridad','mapaseguridad:regiones','OID',newRegion.properties.OID).then(function (result) {
                        var topRight = result.BoundingBox.LowerCorner.__text.split(" ");
                        var bottomLeft = result.BoundingBox.UpperCorner.__text.split(" ");
                        topRight = ol.proj.fromLonLat([parseFloat(topRight[0]), parseFloat(topRight[1])]);
                        bottomLeft = ol.proj.fromLonLat([parseFloat(bottomLeft[0]), parseFloat(bottomLeft[1])]);
                        var boundingExtent = ol.extent.boundingExtent([topRight, bottomLeft]);
                        map.getView().fit(boundingExtent, map.getSize());
                    });
                }else {
                    vm.cql.region.hm.pop();
                    vm.cql.region.lr.pop();
                    //Center map
                    GeoLayers.getLayerCenterByProperty('mapaseguridad', 'mapaseguridad:jalisco', 'CVE_ENT', 14).then(function (result) {
                        var topRight = result.BoundingBox.LowerCorner.__text.split(" ");
                        var bottomLeft = result.BoundingBox.UpperCorner.__text.split(" ");
                        topRight = ol.proj.fromLonLat([parseFloat(topRight[0]), parseFloat(topRight[1])]);
                        bottomLeft = ol.proj.fromLonLat([parseFloat(bottomLeft[0]), parseFloat(bottomLeft[1])]);
                        var boundingExtent = ol.extent.boundingExtent([topRight, bottomLeft]);
                        map.getView().fit(boundingExtent, map.getSize());
                    });
                }
                heatMapDelitos.getSource().updateParams({"cql_filter": vm.cql.region.hm.slice(-1)});
                regiones.getSource().updateParams({"cql_filter": vm.cql.region.lr.slice(-1)});
            }
        });

        //Fill select municipios
        $scope.$watch('vm.filters.region', function(newRegion, oldRegion) {
            if(newRegion!=oldRegion){
                if(newRegion != undefined) {
                    Geoserver.query({
                        'service': 'wfs',
                        'typename': 'mapaseguridad:municipios',
                        'outputFormat': 'application/json',
                        'request': 'GetFeature',
                        'cql_filter' : "INTERSECTS(the_geom, querySingle('regiones', 'the_geom','OID = "+newRegion.properties.OID+"'))",
                        'PROPERTYNAME': 'cve_mun,nom_mun'
                    }).$promise.then(function (res) {
                        vm.catalogs.municipios = res;
                    });
                }else {
                    vm.filters.municipios = null;
                    vm.catalogs.municipios = null;
                }
            }
        });

        //Update heatMap & municipios layer by municipios field
        $scope.$watch('vm.filters.municipios', function(newMunicipio, oldMunicipio) {
            if (newMunicipio != oldMunicipio) {
                if (newMunicipio != undefined) {
                    municipios.setVisible(true);
                    vm.cql.region.hm.push("WITHIN(position, querySingle('municipios', 'the_geom','cve_mun = "+newMunicipio.properties.cve_mun+"'))");
                    vm.cql.municipios.lm.push("WITHIN(the_geom, querySingle('municipios', 'the_geom','cve_mun = "+newMunicipio.properties.cve_mun+"'))");
                    GeoLayers.getLayerCenterByProperty('mapaseguridad','mapaseguridad:municipios','cve_mun',newMunicipio.properties.cve_mun).then(function (result){
                        var topRight = result.BoundingBox.LowerCorner.__text.split(" ");
                        var bottomLeft = result.BoundingBox.UpperCorner.__text.split(" ");
                        topRight = ol.proj.fromLonLat([parseFloat(topRight[0]),parseFloat(topRight[1])]);
                        bottomLeft = ol.proj.fromLonLat([parseFloat(bottomLeft[0]),parseFloat(bottomLeft[1])]);
                        var boundingExtent = ol.extent.boundingExtent([topRight,bottomLeft]);
                        map.getView().fit(boundingExtent, map.getSize());
                    });
                }else{
                    municipios.setVisible(false);
                    vm.cql.region.hm.pop();
                    vm.cql.municipios.lm.pop();
                    //Center map
                    GeoLayers.getLayerCenterByProperty('mapaseguridad','mapaseguridad:regiones','OID',vm.filters.region.properties.OID).then(function (result) {
                        var topRight = result.BoundingBox.LowerCorner.__text.split(" ");
                        var bottomLeft = result.BoundingBox.UpperCorner.__text.split(" ");
                        topRight = ol.proj.fromLonLat([parseFloat(topRight[0]), parseFloat(topRight[1])]);
                        bottomLeft = ol.proj.fromLonLat([parseFloat(bottomLeft[0]), parseFloat(bottomLeft[1])]);
                        var boundingExtent = ol.extent.boundingExtent([topRight, bottomLeft]);
                        map.getView().fit(boundingExtent, map.getSize());
                    });
                }

                heatMapDelitos.getSource().updateParams({"cql_filter": vm.cql.region.hm.slice(-1)});
                municipios.getSource().updateParams({"cql_filter": vm.cql.municipios.lm.slice(-1)});
            }
        });

        //Fill colonia municipios
        $scope.$watch('vm.filters.municipios', function(newMunicipio, oldMunicipio) {
            if (newMunicipio != oldMunicipio) {
                if (newMunicipio != undefined) {
                    Geoserver.query({
                        'service': 'wfs',
                        'version': '1.1.0',
                        'typename': 'mapaseguridad:colonias',
                        'outputFormat': 'application/json',
                        'request': 'GetFeature',
                        'cql_filter' : vm.cql.municipios.lm.slice(-1),
                        'PROPERTYNAME': 'COLONIA'
                    }).$promise.then(function (res) {
                        vm.catalogs.colonias = res;
                    });
                }else{
                    vm.catalogs.colonias = null;
                    vm.filters.colonia = null;
                    vm.filters.cp = null;
                }
            }
        });

        //Reset cp field
        $scope.$watch('vm.filters.municipios', function(newMunicipio, oldMunicipio) {
            if (newMunicipio != oldMunicipio) {
                if (newMunicipio == undefined) {
                    vm.filters.cp = null;
                }
            }
        });

        //Fill colonia by cp
        $scope.$watch('vm.filters.cp', function(newCP, oldCP) {
            if (newCP != oldCP) {
                if (newCP != undefined) {
                    if(newCP.length == 5){
                        Geoserver.query({
                            'service': 'wfs',
                            'version': '1.1.0',
                            'typename': 'mapaseguridad:colonias',
                            'outputFormat': 'application/json',
                            'request': 'GetFeature',
                            'cql_filter' : vm.cql.municipios.lm.slice(-1) + " AND CP_COLONIA = "+newCP,
                            'PROPERTYNAME': 'COLONIA'
                        }).$promise.then(function (res) {
                            vm.catalogs.colonias = res;
                        });
                        return;
                    }
                }
                if(vm.catalogs.colonias.features.length == 0) {
                    Geoserver.query({
                        'service': 'wfs',
                        'version': '1.1.0',
                        'typename': 'mapaseguridad:colonias',
                        'outputFormat': 'application/json',
                        'request': 'GetFeature',
                        'cql_filter': vm.cql.municipios.lm.slice(-1),
                        'PROPERTYNAME': 'COLONIA'
                    }).$promise.then(function (res) {
                        vm.catalogs.colonias = res;
                    });
                }

            }
        });

        //Update heatMap by colonia
        $scope.$watch('vm.filters.colonia', function(newCol, oldCol) {
            if (newCol != oldCol) {
                if (newCol != undefined) {
                    colonias.setVisible(true);
                    jaliscoContorno.setVisible(false);
                    vm.cql.region.hm.push("WITHIN(position, querySingle('mapaseguridad:colonias', 'the_geom','COLONIA = "+newCol.properties.COLONIA+"'))");
                    vm.cql.colonias.lc.push("WITHIN(the_geom, querySingle('mapaseguridad:colonias', 'the_geom','COLONIA = "+newCol.properties.COLONIA+"'))");
                    GeoLayers.getLayerCenterByProperty('mapaseguridad','mapaseguridad:colonias','COLONIA',newCol.properties.COLONIA).then(function (result){
                        var topRight = result.BoundingBox.LowerCorner.__text.split(" ");
                        var bottomLeft = result.BoundingBox.UpperCorner.__text.split(" ");
                        topRight = ol.proj.fromLonLat([parseFloat(topRight[0]),parseFloat(topRight[1])]);
                        bottomLeft = ol.proj.fromLonLat([parseFloat(bottomLeft[0]),parseFloat(bottomLeft[1])]);
                        var boundingExtent = ol.extent.boundingExtent([topRight,bottomLeft]);
                        map.getView().fit(boundingExtent, map.getSize());
                    });
                }else{
                    vm.cql.region.hm.pop();
                    vm.cql.colonias.lc.pop();
                    colonias.setVisible(false);
                    jaliscoContorno.setVisible(true);
                    //Center map
                    GeoLayers.getLayerCenterByProperty('mapaseguridad','mapaseguridad:municipios','cve_mun',vm.filters.municipios.properties.cve_mun).then(function (result){
                        var topRight = result.BoundingBox.LowerCorner.__text.split(" ");
                        var bottomLeft = result.BoundingBox.UpperCorner.__text.split(" ");
                        topRight = ol.proj.fromLonLat([parseFloat(topRight[0]),parseFloat(topRight[1])]);
                        bottomLeft = ol.proj.fromLonLat([parseFloat(bottomLeft[0]),parseFloat(bottomLeft[1])]);
                        var boundingExtent = ol.extent.boundingExtent([topRight,bottomLeft]);
                        map.getView().fit(boundingExtent, map.getSize());
                    });
                }

                heatMapDelitos.getSource().updateParams({"cql_filter": vm.cql.region.hm.slice(-1)});
                colonias.getSource().updateParams({"cql_filter": vm.cql.colonias.lc.slice(-1)});
            }
        });



        /**
         * Initialize blocks
         */
        vm.blocks.reportes = {};
        vm.blocks.reportes.page = 1;
        vm.blocks.reportes.totalItems = 1;

        function userReports(){
            UserReport.query({
                page: vm.blocks.reportes.page,
                size: vm.itemsPerPage-1,
                sort: 'id,asc'
            }, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.blocks.reportes.totalItems = headers('X-Total-Count');
                vm.blocks.reportes.data = data;
                console.log("test");
                console.log(data);
            }
            function onError(error) {
            }
        }
        userReports();

        $scope.$watch('vm.blocks.reportes.page', function(page, prevPage) {
            if (page != prevPage) {
                userReports();
            }
        });


        //Tabla de municipios
        vm.blocks.im_mun = {};
        vm.blocks.im_mun.page = 1;
        vm.blocks.im_mun.cql = "INCLUDE";
        vm.blocks.im_mun.totalItems = 1;
        vm.blocks.im_mun.itemsPerPage = 8;
        function imMun(){
            Geoserver.query({
                'service': 'wfs',
                'version': '2.0.0',
                'typename': 'mapaseguridad:im_municipio_2010',
                'request': 'GetFeature',
                'startIndex' : vm.blocks.im_mun.page - 1,
                'count': vm.blocks.im_mun.itemsPerPage,
                'CQL_FILTER' : vm.blocks.im_mun.cql,
                'PROPERTYNAME': 'CVE_MPIO,CVE_GEOMUN,CVE_ENT_1,NOM_ENT,CVE_MUN_1,NOM_MUN_1,POB_TOT,TOTVIV,ANALF,SPRIM,' +
                                'OVSDE,OVSEE,OVSAE,VHAC,OVPT,PL_MEN5000,PO2SM,OVSD,OVSDSE,IMM,GMM,ANIO'
            }).$promise.then(function (res) {
                var x2js = new X2JS();
                var queryResult = x2js.xml_str2json(res.xml);
                var numberMatched = queryResult.FeatureCollection._numberMatched;
                vm.blocks.im_mun.totalItems = numberMatched;
                if(numberMatched == 1){
                    vm.blocks.im_mun.result = {'im_municipio_2010':queryResult.FeatureCollection.member};
                }else {
                    vm.blocks.im_mun.result = queryResult.FeatureCollection.member;
                }
            });
        }
        imMun();
        $scope.$watch('vm.blocks.im_mun.page', function(page, prevPage) {
            if (page != prevPage) {
                imMun();
            }
        });
        $scope.$watch('vm.filters.region', function(newRegion, oldRegion) {
            if(newRegion!=oldRegion){
                if(newRegion != undefined)
                    vm.blocks.im_mun.cql = "INTERSECTS(the_geom, querySingle('regiones', 'the_geom','OID = " + newRegion.properties.OID + "'))";
                else
                    vm.blocks.im_mun.cql = "INCLUDE";
                imMun();
            }
        });
        $scope.$watch('vm.filters.municipios', function(newMun, oldMun) {
            if(newMun!=oldMun && newMun!=undefined){
                console.log(newMun.properties.cve_mun);
                vm.blocks.im_mun.cql  = "CVE_MUN_1 = "+parseInt(newMun.properties.cve_mun);
                imMun();
            }else{
            }
        });

        //Grafica de municipios 1
        vm.blocks.im_mun_graph = {};
        vm.blocks.im_mun_graph.cql = "INCLUDE";
        function imMunGraphs(){
            Geoserver.query({
                'service': 'wfs',
                'version': '2.0.0',
                'typename': 'mapaseguridad:im_municipio_2010',
                'outputFormat': 'application/json',
                'request': 'GetFeature',
                'PROPERTYNAME': 'NOM_MUN_1,POB_TOT,ANALF,SPRIM,' +
                'OVSDE,OVSEE,OVSAE,VHAC,OVPT,PL_MEN5000,PO2SM,OVSD,OVSDSE,IMM,GMM,ANIO',
                'sortBy': 'POB_TOT',
                'CQL_FILTER': vm.blocks.im_mun_graph.cql,
            }).$promise.then(function (res) {
                var pobTotalData = [];
                var analfData = [];
                var immData = [];
                $.each(res.features, function (index, value) {
                    var obj = {};
                    obj.name = value.properties.NOM_MUN_1;
                    obj.y = parseFloat(value.properties.POB_TOT.replace(" ",""));
                    pobTotalData.push(obj);
                    analfData.push({'name':value.properties.NOM_MUN_1,
                                    'y':parseFloat(value.properties.ANALF)});
                    immData.push([value.properties.NOM_MUN_1, parseFloat(value.properties.IMM)]);
                });

                vm.blocks.im_mun_graph.pob_tot = {
                    options: {
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            type: 'pie'
                        },
                        title: {
                            text: ' '
                        },
                    },
                    series: [{
                        name: 'Población total',
                        colorByPoint: true,
                        data: pobTotalData,
                    },{
                        name: 'Población total 1',
                        colorByPoint: true,
                        data: pobTotalData,
                    }]
                };

                vm.blocks.im_mun_graph.analfb = {
                    options: {
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            type: 'pie'
                        },
                        title: {
                            text: ' '
                        },
                    },
                    series: [{
                        name: 'Analfabetismo',
                        colorByPoint: true,
                        data: analfData,
                    }]
                };

                vm.blocks.im_mun_graph.imm = {
                    options: {
                        chart: {
                            type: 'column'
                        },
                        title: {
                            text: ' '
                        },
                        xAxis: {
                            type: 'category',
                            labels: {
                                rotation: -45,
                                style: {
                                    fontSize: '13px',
                                    fontFamily: 'Verdana, sans-serif'
                                }
                            }
                        },
                        yAxis: {
                            title: {
                                text: 'Population (millions)'
                            }
                        },
                        legend: {
                            enabled: false
                        }
                    },
                    series: [{
                        name: 'Analfabetismo',
                        data: immData,
                    }]
                };

            });
        }
        imMunGraphs();
        $scope.$watch('vm.filters.region', function(newRegion, oldRegion) {
            if(newRegion!=oldRegion){
                if(newRegion != undefined)
                    vm.blocks.im_mun_graph.cql = "INTERSECTS(the_geom, querySingle('regiones', 'the_geom','OID = " + newRegion.properties.OID + "'))";
                else
                    vm.blocks.im_mun_graph.cql = "INCLUDE";
                imMunGraphs();
            }
        });




        //Gráfica de municipios
        $scope.chartConfig = {
            options: {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: 'Browser market shares January, 2015 to May, 2015'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
            },
            series: [{
                name: 'Brands',
                colorByPoint: true,
                data: [{
                    name: 'Microsoft Internet Explorer',
                    y: 56.33
                }, {
                    name: 'Chrome',
                    y: 24.03,
                    sliced: true,
                    selected: true
                }, {
                    name: 'Firefox',
                    y: 10.38
                }, {
                    name: 'Safari',
                    y: 4.77
                }, {
                    name: 'Opera',
                    y: 0.91
                }, {
                    name: 'Proprietary or Undetectable',
                    y: 0.2
                }]
            }]
        };













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
