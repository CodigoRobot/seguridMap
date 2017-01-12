(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('UserReportDeleteController',UserReportDeleteController);

    UserReportDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserReport'];

    function UserReportDeleteController($uibModalInstance, entity, UserReport) {
        var vm = this;

        vm.userReport = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserReport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
