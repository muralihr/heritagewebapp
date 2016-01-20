'use strict';

angular.module('heritageMapperAppApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status == 401 && response.data.path.indexOf("/api/account") == 0 )){
	                $rootScope.$emit('heritageMapperAppApp.httpError', response);
	            }
                return $q.reject(response);
            }
        };
    });