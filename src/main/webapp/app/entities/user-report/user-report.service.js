(function() {
    'use strict';
    angular
        .module('seguridMapApp')
        .factory('UserReport', UserReport);

    UserReport.$inject = ['$resource', 'DateUtils'];

    function UserReport ($resource, DateUtils) {
        var resourceUrl =  'api/user-reports/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaDelitoPolicia = DateUtils.convertDateTimeFromServer(data.fechaDelitoPolicia);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
