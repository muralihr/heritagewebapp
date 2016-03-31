'use strict';

angular.module('heritageMapperAppApp')
    .factory('HeritageGroupUser', function ($resource, DateUtils) {
        return $resource('api/heritageGroupUsers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
