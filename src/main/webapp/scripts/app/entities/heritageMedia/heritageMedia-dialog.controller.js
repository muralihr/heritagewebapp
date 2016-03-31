'use strict';

angular.module('heritageMapperAppApp').controller('HeritageMediaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'HeritageMedia', 'HeritageCategory', 'HeritageLanguage', 'HeritageGroup', 'HeritageApp', 'User',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, HeritageMedia, HeritageCategory, HeritageLanguage, HeritageGroup, HeritageApp, User) {

        $scope.heritageMedia = entity;
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        $scope.heritagegroups = HeritageGroup.query();
        $scope.heritageapps = HeritageApp.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            HeritageMedia.get({id : id}, function(result) {
                $scope.heritageMedia = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritagemapperappApp:heritageMediaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageMedia.id != null) {
                HeritageMedia.update($scope.heritageMedia, onSaveSuccess, onSaveError);
            } else {
                HeritageMedia.save($scope.heritageMedia, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setMediaFile = function ($file, heritageMedia) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        heritageMedia.mediaFile = base64Data;
                        heritageMedia.mediaFileContentType = $file.type;
                    });
                };
            }
        };
        $scope.datePickerForUploadTime = {};

        $scope.datePickerForUploadTime.status = {
            opened: false
        };

        $scope.datePickerForUploadTimeOpen = function($event) {
            $scope.datePickerForUploadTime.status.opened = true;
        };
}]);
