'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageWalkDetailController', function ($scope, $rootScope, $stateParams, entity, HeritageWalk, User) {
        $scope.heritageWalk = entity;
        $scope.load = function (id) {
            HeritageWalk.get({id: id}, function(result) {
                $scope.heritageWalk = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritagemapperappApp:heritageWalkUpdate', function(event, result) {
            $scope.heritageWalk = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
