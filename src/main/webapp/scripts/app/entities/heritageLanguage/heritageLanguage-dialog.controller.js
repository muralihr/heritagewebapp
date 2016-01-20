'use strict';

angular.module('heritageMapperAppApp').controller('HeritageLanguageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HeritageLanguage',
        function($scope, $stateParams, $uibModalInstance, entity, HeritageLanguage) {

        $scope.heritageLanguage = entity;
        $scope.load = function(id) {
            HeritageLanguage.get({id : id}, function(result) {
                $scope.heritageLanguage = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritageMapperAppApp:heritageLanguageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageLanguage.id != null) {
                HeritageLanguage.update($scope.heritageLanguage, onSaveSuccess, onSaveError);
            } else {
                HeritageLanguage.save($scope.heritageLanguage, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
