'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageMediaDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, HeritageMedia, HeritageCategory, HeritageLanguage, HeritageGroup, HeritageApp, User) {
        $scope.heritageMedia = entity;
        $scope.load = function (id) {
            HeritageMedia.get({id: id}, function(result) {
                $scope.heritageMedia = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritagemapperappApp:heritageMediaUpdate', function(event, result) {
            $scope.heritageMedia = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
