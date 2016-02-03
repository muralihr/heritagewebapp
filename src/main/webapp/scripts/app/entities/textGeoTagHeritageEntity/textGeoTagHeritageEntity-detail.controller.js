'use strict';

angular.module('heritageMapperAppApp')
    .controller('TextGeoTagHeritageEntityDetailController', function ($scope, $rootScope, $stateParams, entity, TextGeoTagHeritageEntity, HeritageCategory, HeritageLanguage, User) {
        $scope.textGeoTagHeritageEntity = entity;
        $scope.load = function (id) {
            TextGeoTagHeritageEntity.get({id: id}, function(result) {
                $scope.textGeoTagHeritageEntity = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritageMapperAppApp:textGeoTagHeritageEntityUpdate', function(event, result) {
            $scope.textGeoTagHeritageEntity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
