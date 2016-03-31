'use strict';

angular.module('heritageMapperAppApp').controller('HeritageRegionNameDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HeritageRegionName',
        function($scope, $stateParams, $uibModalInstance, entity, HeritageRegionName) {

        $scope.heritageRegionName = entity;
        $scope.load = function(id) {
            HeritageRegionName.get({id : id}, function(result) {
                $scope.heritageRegionName = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritageMapperAppApp:heritageRegionNameUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageRegionName.id != null) {
                HeritageRegionName.update($scope.heritageRegionName, onSaveSuccess, onSaveError);
            } else {
                HeritageRegionName.save($scope.heritageRegionName, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
