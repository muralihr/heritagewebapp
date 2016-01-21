'use strict';

angular.module('heritageMapperAppApp').controller('TextGeoTagHeritageEntityDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TextGeoTagHeritageEntity', 'HeritageCategory', 'sharedGeoProperties', 'HeritageLanguage',
        function($scope, $stateParams, $uibModalInstance, entity, TextGeoTagHeritageEntity, HeritageCategory, sharedGeoProperties,HeritageLanguage) {

        $scope.textGeoTagHeritageEntity = entity;
        $scope.heritagecategorys = HeritageCategory.query();
        $scope.heritagelanguages = HeritageLanguage.query();
        
        $scope.textGeoTagHeritageEntity.latitude = sharedGeoProperties.getLatitude();
        $scope.textGeoTagHeritageEntity.longitude = sharedGeoProperties.getLongitude(); 
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
}]);
