(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('UserReportDetailController', UserReportDetailController);

    UserReportDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserReport', 'User'];

    function UserReportDetailController($scope, $rootScope, $stateParams, previousState, entity, UserReport, User) {
        var vm = this;

        vm.userReport = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('seguridMapApp:userReportUpdate', function(event, result) {
            vm.userReport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
