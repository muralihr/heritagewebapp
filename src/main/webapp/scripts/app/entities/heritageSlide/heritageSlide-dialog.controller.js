'use strict';

angular.module('heritageMapperAppApp').controller('HeritageSlideDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HeritageSlide', 'HeritageMedia', 'HeritageWalk', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, HeritageSlide, HeritageMedia, HeritageWalk, User) {

        $scope.heritageSlide = entity;
        $scope.heritagemedias = HeritageMedia.query();
        $scope.heritagewalks = HeritageWalk.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            HeritageSlide.get({id : id}, function(result) {
                $scope.heritageSlide = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('heritagemapperappApp:heritageSlideUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heritageSlide.id != null) {
                HeritageSlide.update($scope.heritageSlide, onSaveSuccess, onSaveError);
            } else {
                HeritageSlide.save($scope.heritageSlide, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
