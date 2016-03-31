'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageGroupUserDetailController', function ($scope, $rootScope, $stateParams, entity, HeritageGroupUser, HeritageGroup, User) {
        $scope.heritageGroupUser = entity;
        $scope.load = function (id) {
            HeritageGroupUser.get({id: id}, function(result) {
                $scope.heritageGroupUser = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritagemapperappApp:heritageGroupUserUpdate', function(event, result) {
            $scope.heritageGroupUser = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
