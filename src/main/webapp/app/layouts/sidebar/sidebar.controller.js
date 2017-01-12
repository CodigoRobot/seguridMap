(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .controller('SideBarController', SideBarController);

    SideBarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService','Panel','leafletData'];

    function SideBarController ($state, Auth, Principal, ProfileService,Panel,leafletData) {
        var vm = this;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.go = go;
        vm.logout = logout;
        vm.user = {};

        function logout() {
            Auth.logout();
            $state.go('home');
            leafletData.getMap("main1").then(
                function (map) {
                    setTimeout(function(){ map.invalidateSize()}, 400);
                }
            );
        }

        Principal.identity().then(function (user) {
            vm.user = user;
        });

        Panel.query({
            sort: 'id,asc'
        }, function onSuccess(data, headers) {
            vm.panels = data;
            console.log(data);
        }, function onError(error) {

        });

        function go(panel) {
            $state.go("page-panel",{id:panel.id});
        }

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

    }
})();
