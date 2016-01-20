'use strict';

angular.module('heritageMapperAppApp')
    .controller('VideoGeoTagHeritageEntityDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, VideoGeoTagHeritageEntity, HeritageCategory, HeritageLanguage) {
        $scope.videoGeoTagHeritageEntity = entity;
        $scope.load = function (id) {
            VideoGeoTagHeritageEntity.get({id: id}, function(result) {
                $scope.videoGeoTagHeritageEntity = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritageMapperAppApp:videoGeoTagHeritageEntityUpdate', function(event, result) {
            $scope.videoGeoTagHeritageEntity = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
