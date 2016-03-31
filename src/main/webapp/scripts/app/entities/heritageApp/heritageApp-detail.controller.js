'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageAppDetailController', function ($scope, $rootScope, $stateParams, entity, HeritageApp, HeritageRegionName, HeritageGroup, HeritageLanguage, HeritageCategory) {
        $scope.heritageApp = entity;
        $scope.load = function (id) {
            HeritageApp.get({id: id}, function(result) {
                $scope.heritageApp = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritagemapperappApp:heritageAppUpdate', function(event, result) {
            $scope.heritageApp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
