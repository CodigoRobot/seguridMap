(function() {
    'use strict';
    angular
        .module('seguridMapApp')
        .factory('Wms', Wms);

    Wms.$inject = ['$resource'];

    function Wms ($resource) {
        var resourceUrl =  'api/wms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
