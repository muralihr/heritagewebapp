'use strict';

angular.module('heritageMapperAppApp')
    .factory('HeritageRegionName', function ($resource, DateUtils) {
        return $resource('api/heritageRegionNames/:id', {}, {
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
