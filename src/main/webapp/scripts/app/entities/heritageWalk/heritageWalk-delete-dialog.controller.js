'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageWalkDeleteController', function($scope, $uibModalInstance, entity, HeritageWalk) {

        $scope.heritageWalk = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageWalk.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
