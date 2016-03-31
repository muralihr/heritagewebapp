'use strict';

angular.module('heritageMapperAppApp').controller('HeritageGroupUserDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HeritageGroupUser', 'HeritageGroup', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, HeritageGroupUser, HeritageGroup, User) {

        $scope.heritageGroupUser = entity;
        $scope.heritagegroups = HeritageGroup.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            HeritageGroupUser.get({id : id}, function(result) {
                $scope.heritageGroupUser = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritagemapperappApp:heritageGroupUserUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageGroupUser.id != null) {
                HeritageGroupUser.update($scope.heritageGroupUser, onSaveSuccess, onSaveError);
            } else {
                HeritageGroupUser.save($scope.heritageGroupUser, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
