'use strict';

angular.module('heritageMapperAppApp')
    .factory('HeritageMedia', function ($resource, DateUtils) {
        return $resource('api/heritageMedias/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.uploadTime = DateUtils.convertDateTimeFromServer(data.uploadTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
