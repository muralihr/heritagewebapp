'use strict';

angular.module('heritageMapperAppApp')
    .factory('AudioGeoTagHeritageEntity', function ($resource, DateUtils) {
        return $resource('api/audioGeoTagHeritageEntitys/:id', {}, {
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
