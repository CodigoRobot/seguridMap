(function() {
    'use strict';

    angular
        .module('seguridMapApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('map', {
            abstract: true,
            parent: 'app'
        });
    }
})();
