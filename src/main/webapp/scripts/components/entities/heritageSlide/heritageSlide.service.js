'use strict';

angular.module('heritageMapperAppApp')
    .factory('HeritageSlide', function ($resource, DateUtils) {
        return $resource('api/heritageSlides/:id', {}, {
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
