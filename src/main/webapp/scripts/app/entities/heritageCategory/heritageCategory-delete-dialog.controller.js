'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageCategoryDeleteController', function($scope, $uibModalInstance, entity, HeritageCategory) {

        $scope.heritageCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
