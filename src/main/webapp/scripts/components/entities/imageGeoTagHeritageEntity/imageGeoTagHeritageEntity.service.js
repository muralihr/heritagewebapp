'use strict';

angular.module('heritageMapperAppApp')
    .factory('ImageGeoTagHeritageEntity', function ($resource, DateUtils) {
        return $resource('api/imageGeoTagHeritageEntitys/:id', {}, {
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
