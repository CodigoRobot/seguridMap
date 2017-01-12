(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('UserMapController', UserMapController);

    UserMapController.$inject = ['$scope', '$state', 'AlertService','leafletData'];

    function UserMapController ($scope, $state, AlertService,leafletData) {
        var vm = this;

        angular.extend($scope, {
            defaults: {
                scrollWheelZoom: false
            }
        });


        //Chart.js example
        //With directive
        //https://jtblin.github.io/angular-chart.js/#getting_started
        $scope.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
        $scope.data = [300, 500, 100];



        //Use
        //http://tombatossals.github.io/angular-leaflet-directive/examples/0000-viewer.html#/basic/first-example
        //Or
        leafletData.getMap("m1").then(
            function (map) {
                //Map object
                map.setView(new L.LatLng(40.737, -73.923), 8);
            }
        );
        leafletData.getMap("m2").then(
            function (map) {
                //Map object
                map.setView(new L.LatLng(20.6739383, -103.405625), 8);
            }
        );

    }
})();
