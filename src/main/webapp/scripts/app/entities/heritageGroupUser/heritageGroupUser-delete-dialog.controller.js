'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageGroupUserDeleteController', function($scope, $uibModalInstance, entity, HeritageGroupUser) {

        $scope.heritageGroupUser = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageGroupUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
