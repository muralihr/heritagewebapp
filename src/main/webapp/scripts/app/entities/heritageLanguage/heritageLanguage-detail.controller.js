'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageLanguageDetailController', function ($scope, $rootScope, $stateParams, entity, HeritageLanguage) {
        $scope.heritageLanguage = entity;
        $scope.load = function (id) {
            HeritageLanguage.get({id: id}, function(result) {
                $scope.heritageLanguage = result;
            });
        };
        var unsubscribe = $rootScope.$on('heritageMapperAppApp:heritageLanguageUpdate', function(event, result) {
            $scope.heritageLanguage = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
