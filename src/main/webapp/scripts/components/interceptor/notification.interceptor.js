 'use strict';

angular.module('heritageMapperAppApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-heritageMapperAppApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-heritageMapperAppApp-params')});
                }
                return response;
            }
        };
    });
