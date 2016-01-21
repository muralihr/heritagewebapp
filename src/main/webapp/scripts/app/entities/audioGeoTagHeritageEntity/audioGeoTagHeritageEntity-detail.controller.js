'use strict';

angular.module('heritageMapperAppApp')
    .controller('AudioGeoTagHeritageEntityDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, AudioGeoTagHeritageEntity, HeritageCategory, HeritageLanguage) {
        $scope.audioGeoTagHeritageEntity = entity;
        $scope.load = function (id) {
            AudioGeoTagHeritageEntity.get({id: id}, function(result) {
                $scope.audioGeoTagHeritageEntity = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritageMapperAppApp:audioGeoTagHeritageEntityUpdate', function(event, result) {
            $scope.audioGeoTagHeritageEntity = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
