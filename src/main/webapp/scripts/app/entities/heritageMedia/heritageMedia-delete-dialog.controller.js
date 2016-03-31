'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageMediaDeleteController', function($scope, $uibModalInstance, entity, HeritageMedia) {

        $scope.heritageMedia = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageMedia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
