'use strict';

angular.module('heritageMapperAppApp').controller('VideoGeoTagHeritageEntityDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'VideoGeoTagHeritageEntity', 'HeritageCategory', 'HeritageLanguage','sharedGeoProperties',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, VideoGeoTagHeritageEntity, HeritageCategory, HeritageLanguage,sharedGeoProperties  ) {

        $scope.videoGeoTagHeritageEntity = entity;
        $scope.videoGeoTagHeritageEntity.latitude = sharedGeoProperties.getLatitude();
        $scope.videoGeoTagHeritageEntity.longitude = sharedGeoProperties.getLongitude(); 
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        $scope.load = function(id) {
         
            VideoGeoTagHeritageEntity.get({id : id}, function(result) {
                $scope.videoGeoTagHeritageEntity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritageMapperAppApp:videoGeoTagHeritageEntityUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.videoGeoTagHeritageEntity.id != null) {
                VideoGeoTagHeritageEntity.update($scope.videoGeoTagHeritageEntity, onSaveSuccess, onSaveError);
            } else {
                VideoGeoTagHeritageEntity.save($scope.videoGeoTagHeritageEntity, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setVideoFile = function ($file, videoGeoTagHeritageEntity) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        videoGeoTagHeritageEntity.videoFile = base64Data;
                        videoGeoTagHeritageEntity.urlOrfileLink = $file.name;
                        console.log("file name of video"+$file.name);
                        videoGeoTagHeritageEntity.videoFileContentType = $file.type;
                    });
                };
            }
        };
}]);
