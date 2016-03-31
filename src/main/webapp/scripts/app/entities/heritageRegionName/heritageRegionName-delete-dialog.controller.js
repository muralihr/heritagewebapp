'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageRegionNameDeleteController', function($scope, $uibModalInstance, entity, HeritageRegionName) {

        $scope.heritageRegionName = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageRegionName.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
