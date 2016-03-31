'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageGroupDeleteController', function($scope, $uibModalInstance, entity, HeritageGroup) {

        $scope.heritageGroup = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
