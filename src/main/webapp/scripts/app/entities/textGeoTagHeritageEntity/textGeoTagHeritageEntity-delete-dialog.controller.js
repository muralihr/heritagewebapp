'use strict';

angular.module('heritageMapperAppApp')
	.controller('TextGeoTagHeritageEntityDeleteController', function($scope, $uibModalInstance, entity, TextGeoTagHeritageEntity) {

        $scope.textGeoTagHeritageEntity = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TextGeoTagHeritageEntity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
