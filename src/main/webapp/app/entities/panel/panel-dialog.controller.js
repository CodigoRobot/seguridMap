(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('PanelDialogController', PanelDialogController);

    PanelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Panel'];

    function PanelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Panel) {
        var vm = this;

        vm.panel = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.panel.id !== null) {
                Panel.update(vm.panel, onSaveSuccess, onSaveError);
            } else {
                Panel.save(vm.panel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('seguridMapApp:panelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
