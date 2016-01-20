'use strict';

angular.module('heritageMapperAppApp').controller('ImageGeoTagHeritageEntityDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ImageGeoTagHeritageEntity', 'HeritageCategory', 'sharedGeoProperties','HeritageLanguage',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, ImageGeoTagHeritageEntity, HeritageCategory,sharedGeoProperties, HeritageLanguage) {

        $scope.imageGeoTagHeritageEntity = entity;
        
        $scope.imageGeoTagHeritageEntity.latitude = sharedGeoProperties.getLatitude();
        $scope.imageGeoTagHeritageEntity.longitude = sharedGeoProperties.getLongitude(); 
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        $scope.load = function(id) {
            ImageGeoTagHeritageEntity.get({id : id}, function(result) {
                $scope.imageGeoTagHeritageEntity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritageMapperAppApp:imageGeoTagHeritageEntityUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.imageGeoTagHeritageEntity.id != null) {
                ImageGeoTagHeritageEntity.update($scope.imageGeoTagHeritageEntity, onSaveSuccess, onSaveError);
            } else {
                ImageGeoTagHeritageEntity.save($scope.imageGeoTagHeritageEntity, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setPhoto = function ($file, imageGeoTagHeritageEntity) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        imageGeoTagHeritageEntity.photo = base64Data;
                        imageGeoTagHeritageEntity.photoContentType = $file.type;
                        
                        imageGeoTagHeritageEntity.urlOrfileLink = $file.name;
                    });
                };
            }
        };
}]);
