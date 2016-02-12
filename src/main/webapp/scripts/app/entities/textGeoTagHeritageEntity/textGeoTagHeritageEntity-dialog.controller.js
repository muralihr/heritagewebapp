'use strict';

angular.module('heritageMapperAppApp').controller('TextGeoTagHeritageEntityDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TextGeoTagHeritageEntity', 'HeritageCategory', 'HeritageLanguage', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, TextGeoTagHeritageEntity, HeritageCategory, HeritageLanguage, User) {

        $scope.textGeoTagHeritageEntity = entity;
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            TextGeoTagHeritageEntity.get({id : id}, function(result) {
                $scope.textGeoTagHeritageEntity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritageMapperAppApp:textGeoTagHeritageEntityUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.textGeoTagHeritageEntity.id != null) {
                TextGeoTagHeritageEntity.update($scope.textGeoTagHeritageEntity, onSaveSuccess, onSaveError);
            } else {
                TextGeoTagHeritageEntity.save($scope.textGeoTagHeritageEntity, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForUploadTime = {};

        $scope.datePickerForUploadTime.status = {
            opened: false
        };

        $scope.datePickerForUploadTimeOpen = function($event) {
            $scope.datePickerForUploadTime.status.opened = true;
        };
}]);
