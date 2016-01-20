'use strict';

angular.module('heritageMapperAppApp')
	.controller('ImageGeoTagHeritageEntityDeleteController', function($scope, $uibModalInstance, entity, ImageGeoTagHeritageEntity) {

        $scope.imageGeoTagHeritageEntity = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ImageGeoTagHeritageEntity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
