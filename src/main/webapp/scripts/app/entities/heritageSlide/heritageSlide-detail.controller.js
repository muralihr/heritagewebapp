'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageSlideDetailController', function ($scope, $rootScope, $stateParams, entity, HeritageSlide, HeritageMedia, HeritageWalk, User) {
        $scope.heritageSlide = entity;
        $scope.load = function (id) {
            HeritageSlide.get({id: id}, function(result) {
                $scope.heritageSlide = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritagemapperappApp:heritageSlideUpdate', function(event, result) {
            $scope.heritageSlide = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
