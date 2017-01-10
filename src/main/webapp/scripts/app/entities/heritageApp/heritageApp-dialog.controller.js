'use strict';

angular.module('heritageMapperAppApp').controller('HeritageAppDialogController',
    ['$scope','$http','$stateParams', '$uibModalInstance', 'entity', 'HeritageApp', 'HeritageRegionName', 'HeritageGroup', 'HeritageLanguage', 'HeritageCategory',
        function($scope, $http, $stateParams, $uibModalInstance, entity, HeritageApp, HeritageRegionName, HeritageGroup, HeritageLanguage, HeritageCategory) {

        $scope.heritageApp = entity;
        $scope.heritageregionnames = null;// HeritageRegionName.query();
        $scope.heritagegroups = null;// HeritageGroup.query();
        $scope.heritagelanguages = null;//HeritageLanguage.query();
        $scope.heritagecategorys = null;        
       
        $http.get("api/heritageCategorys2").success(function(data) {
              $scope.heritagecategorys = data;
        });

        $http.get("api/heritageRegionNames2").success(function(data) {
              $scope.heritageregionnames = data;
        });

        $http.get("api/heritageLanguages2").success(function(data) {
              $scope.heritagelanguages = data;
        });

        $http.get("api/heritageGroups2").success(function(data) {
              $scope.heritagegroups = data;
        });
        $scope.load = function(id) {
            HeritageApp.get({id : id}, function(result) {
                $scope.heritageApp = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritagemapperappApp:heritageAppUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageApp.id != null) {
                HeritageApp.update($scope.heritageApp, onSaveSuccess, onSaveError);
            } else {
                HeritageApp.save($scope.heritageApp, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
