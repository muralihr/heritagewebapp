'use strict';

angular.module('heritageMapperAppApp').controller('HeritageWalkDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HeritageWalk', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, HeritageWalk, User) {

        $scope.heritageWalk = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            HeritageWalk.get({id : id}, function(result) {
                $scope.heritageWalk = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritagemapperappApp:heritageWalkUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageWalk.id != null) {
                HeritageWalk.update($scope.heritageWalk, onSaveSuccess, onSaveError);
            } else {
                HeritageWalk.save($scope.heritageWalk, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
