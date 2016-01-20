'use strict';

angular.module('heritageMapperAppApp')
	.controller('VideoGeoTagHeritageEntityDeleteController', function($scope, $uibModalInstance, entity, VideoGeoTagHeritageEntity) {

        $scope.videoGeoTagHeritageEntity = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VideoGeoTagHeritageEntity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
