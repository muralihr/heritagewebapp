'use strict';

angular.module('heritageMapperAppApp').controller('HeritageGroupDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'HeritageGroup',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, HeritageGroup) {

        $scope.heritageGroup = entity;
        $scope.load = function(id) {
            HeritageGroup.get({id : id}, function(result) {
                $scope.heritageGroup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritagemapperappApp:heritageGroupUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageGroup.id != null) {
                HeritageGroup.update($scope.heritageGroup, onSaveSuccess, onSaveError);
            } else {
                HeritageGroup.save($scope.heritageGroup, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setIcon = function ($file, heritageGroup) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        heritageGroup.icon = base64Data;
                        heritageGroup.iconContentType = $file.type;
                    });
                };
            }
        };
}]);
