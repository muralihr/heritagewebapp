'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageGroupEditorDetailController', function ($scope, $rootScope, $stateParams, entity, HeritageGroupEditor, HeritageGroup, User) {
        $scope.heritageGroupEditor = entity;
        $scope.load = function (id) {
            HeritageGroupEditor.get({id: id}, function(result) {
                $scope.heritageGroupEditor = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritagemapperappApp:heritageGroupEditorUpdate', function(event, result) {
            $scope.heritageGroupEditor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
