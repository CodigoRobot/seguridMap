(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('WmsDetailController', WmsDetailController);

    WmsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Wms', 'Panel'];

    function WmsDetailController($scope, $rootScope, $stateParams, previousState, entity, Wms, Panel) {
        var vm = this;

        vm.wms = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('seguridMapApp:wmsUpdate', function(event, result) {
            vm.wms = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
