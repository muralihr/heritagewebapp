'use strict';

angular.module('heritageMapperAppApp')
    .factory('HeritageCategory', function ($resource, DateUtils) {
        return $resource('api/heritageCategorys/:id', {}, {
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
