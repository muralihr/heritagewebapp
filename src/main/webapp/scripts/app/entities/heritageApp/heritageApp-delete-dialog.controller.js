'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageAppDeleteController', function($scope, $uibModalInstance, entity, HeritageApp) {

        $scope.heritageApp = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageApp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
