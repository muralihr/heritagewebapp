'use strict';

angular.module('heritageMapperAppApp').controller('HeritageGroupEditorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HeritageGroupEditor', 'HeritageGroup', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, HeritageGroupEditor, HeritageGroup, User) {

        $scope.heritageGroupEditor = entity;
        $scope.heritagegroups = HeritageGroup.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            HeritageGroupEditor.get({id : id}, function(result) {
                $scope.heritageGroupEditor = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritagemapperappApp:heritageGroupEditorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageGroupEditor.id != null) {
                HeritageGroupEditor.update($scope.heritageGroupEditor, onSaveSuccess, onSaveError);
            } else {
                HeritageGroupEditor.save($scope.heritageGroupEditor, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
