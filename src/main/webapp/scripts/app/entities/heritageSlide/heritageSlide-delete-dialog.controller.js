'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageSlideDeleteController', function($scope, $uibModalInstance, entity, HeritageSlide) {

        $scope.heritageSlide = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageSlide.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
