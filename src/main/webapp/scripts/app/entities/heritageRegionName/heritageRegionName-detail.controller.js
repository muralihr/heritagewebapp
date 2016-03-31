'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageRegionNameDetailController', function ($scope, $rootScope, $stateParams, entity, HeritageRegionName) {
        $scope.heritageRegionName = entity;
        $scope.load = function (id) {
            HeritageRegionName.get({id: id}, function(result) {
                $scope.heritageRegionName = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritagemapperappApp:heritageRegionNameUpdate', function(event, result) {
            $scope.heritageRegionName = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
