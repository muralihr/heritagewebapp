'use strict';

angular.module('heritageMapperAppApp').controller('HeritageCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'HeritageCategory',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, HeritageCategory) {

        $scope.heritageCategory = entity;
        $scope.load = function(id) {
            HeritageCategory.get({id : id}, function(result) {
                $scope.heritageCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritageMapperAppApp:heritageCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageCategory.id != null) {
                HeritageCategory.update($scope.heritageCategory, onSaveSuccess, onSaveError);
            } else {
                HeritageCategory.save($scope.heritageCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCategoryIcon = function ($file, heritageCategory) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        heritageCategory.categoryIcon = base64Data;
                        heritageCategory.categoryIconContentType = $file.type;
                    });
                };
            }
        };
}]);
