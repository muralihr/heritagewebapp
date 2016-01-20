'use strict';

angular.module('heritageMapperAppApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


