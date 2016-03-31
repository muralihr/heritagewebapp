'use strict';

angular.module('heritageMapperAppApp').controller('HeritageAppDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HeritageApp', 'HeritageRegionName', 'HeritageGroup', 'HeritageLanguage', 'HeritageCategory',
        function($scope, $stateParams, $uibModalInstance, entity, HeritageApp, HeritageRegionName, HeritageGroup, HeritageLanguage, HeritageCategory) {

        $scope.heritageApp = entity;
        $scope.heritageregionnames = HeritageRegionName.query();
        $scope.heritagegroups = HeritageGroup.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        $scope.heritagecategorys = HeritageCategory.query();
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
