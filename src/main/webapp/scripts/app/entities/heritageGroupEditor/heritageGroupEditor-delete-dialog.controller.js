'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageGroupEditorDeleteController', function($scope, $uibModalInstance, entity, HeritageGroupEditor) {

        $scope.heritageGroupEditor = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageGroupEditor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
