'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageGroupDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, HeritageGroup) {
        $scope.heritageGroup = entity;
        $scope.load = function (id) {
            HeritageGroup.get({id: id}, function(result) {
                $scope.heritageGroup = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritagemapperappApp:heritageGroupUpdate', function(event, result) {
            $scope.heritageGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
