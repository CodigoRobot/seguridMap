(function() {
    'use strict';

    angular
        .module('seguridMapApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'highcharts-ng',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'openlayers-directive',
            'leaflet-directive',
            'angular-loading-bar',
            'chart.js',
            'bw.paging'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
