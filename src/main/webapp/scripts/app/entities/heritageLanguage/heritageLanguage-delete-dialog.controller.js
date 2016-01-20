'use strict';

angular.module('heritageMapperAppApp')
	.controller('HeritageLanguageDeleteController', function($scope, $uibModalInstance, entity, HeritageLanguage) {

        $scope.heritageLanguage = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeritageLanguage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
