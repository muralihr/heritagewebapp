'use strict';

angular.module('heritageMapperAppApp')
	.controller('AudioGeoTagHeritageEntityDeleteController', function($scope, $uibModalInstance, entity, AudioGeoTagHeritageEntity) {

        $scope.audioGeoTagHeritageEntity = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AudioGeoTagHeritageEntity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
