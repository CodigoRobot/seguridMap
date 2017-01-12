(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('UserReportDialogController', UserReportDialogController);

    UserReportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserReport', 'User'];

    function UserReportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserReport, User) {
        var vm = this;

        vm.userReport = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userReport.id !== null) {
                UserReport.update(vm.userReport, onSaveSuccess, onSaveError);
            } else {
                UserReport.save(vm.userReport, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('seguridMapApp:userReportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaDelitoPolicia = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
