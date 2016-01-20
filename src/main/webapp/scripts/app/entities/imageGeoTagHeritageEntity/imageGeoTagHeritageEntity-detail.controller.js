'use strict';

angular.module('heritageMapperAppApp')
    .controller('ImageGeoTagHeritageEntityDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, ImageGeoTagHeritageEntity, HeritageCategory, HeritageLanguage) {
        $scope.imageGeoTagHeritageEntity = entity;
        $scope.load = function (id) {
            ImageGeoTagHeritageEntity.get({id: id}, function(result) {
                $scope.imageGeoTagHeritageEntity = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritageMapperAppApp:imageGeoTagHeritageEntityUpdate', function(event, result) {
            $scope.imageGeoTagHeritageEntity = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
