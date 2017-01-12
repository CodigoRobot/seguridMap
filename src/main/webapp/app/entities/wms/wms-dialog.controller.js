(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('WmsDialogController', WmsDialogController);

    WmsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Wms', 'Panel'];

    function WmsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Wms, Panel) {
        var vm = this;

        vm.wms = entity;
        vm.clear = clear;
        vm.save = save;
        vm.panels = Panel.query();
        vm.describeFeature = describeFeature;

        function describeFeature() {
            alert(vm.wms.capa);
            vm.wms.configuracion.id = "da";
        }



        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.wms.id !== null) {
                Wms.update(vm.wms, onSaveSuccess, onSaveError);
            } else {
                Wms.save(vm.wms, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('seguridMapApp:wmsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
