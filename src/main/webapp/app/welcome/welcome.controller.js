(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('WelcomeController', WelcomeController);

    WelcomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'olData', 'Home', 'UserReport', 'Geoserver', 'GeoLayers'];

    function WelcomeController($scope, Principal, LoginService, $state, olData, Home, UserReport, Geoserver, GeoLayers) {
        var vm = this;

        vm.$state = $state;
        vm.goDashboard = goDashboard;
        vm.register = register;
        vm.login = login;
        function goDashboard() {
            $state.go("home");
        }
        function register() {
            $state.go("register");
        }
        function login() {
            $state.go("login");
        }
    }

})();
