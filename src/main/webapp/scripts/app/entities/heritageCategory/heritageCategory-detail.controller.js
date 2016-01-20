'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageCategoryDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, HeritageCategory) {
        $scope.heritageCategory = entity;
        $scope.load = function (id) {
            HeritageCategory.get({id: id}, function(result) {
                $scope.heritageCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritageMapperAppApp:heritageCategoryUpdate', function(event, result) {
            $scope.heritageCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
