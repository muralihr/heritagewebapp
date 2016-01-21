'use strict';

angular.module('heritageMapperAppApp').controller('AudioGeoTagHeritageEntityDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'AudioGeoTagHeritageEntity', 'HeritageCategory', 'sharedGeoProperties', 'HeritageLanguage',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, AudioGeoTagHeritageEntity, HeritageCategory, sharedGeoProperties,HeritageLanguage) {

        $scope.audioGeoTagHeritageEntity = entity;
        
        $scope.audioGeoTagHeritageEntity.latitude = sharedGeoProperties.getLatitude(); 
        $scope.audioGeoTagHeritageEntity.longitude = sharedGeoProperties.getLongitude();  
        
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        $scope.load = function(id) {
            AudioGeoTagHeritageEntity.get({id : id}, function(result) {
                $scope.audioGeoTagHeritageEntity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritageMapperAppApp:audioGeoTagHeritageEntityUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.audioGeoTagHeritageEntity.id != null) {
                AudioGeoTagHeritageEntity.update($scope.audioGeoTagHeritageEntity, onSaveSuccess, onSaveError);
            } else {
                AudioGeoTagHeritageEntity.save($scope.audioGeoTagHeritageEntity, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setAudioFile = function ($file, audioGeoTagHeritageEntity) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        audioGeoTagHeritageEntity.audioFile = base64Data;
                        audioGeoTagHeritageEntity.audioFileContentType = $file.type;
                        audioGeoTagHeritageEntity.urlOrfileLink = $file.name;
                    });
                };
            }
        };
}]);
