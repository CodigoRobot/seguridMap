(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('PanelDetailController', PanelDetailController);

    PanelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Panel'];

    function PanelDetailController($scope, $rootScope, $stateParams, previousState, entity, Panel) {
        var vm = this;

        vm.panel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('seguridMapApp:panelUpdate', function(event, result) {
            vm.panel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
