(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('user-map', {
                parent: 'map',
                url: '/user-map',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'seguridMapApp.userReport.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/maps/user-map/user-map.html',
                        controller: 'UserMapController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('user-map');
                        return $translate.refresh();
                    }]
                }
            });
    }

})();
