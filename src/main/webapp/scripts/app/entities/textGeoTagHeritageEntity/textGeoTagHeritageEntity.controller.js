'use strict';

angular.module('heritageMapperAppApp')
    .controller('TextGeoTagHeritageEntityController', function ($scope, $state, TextGeoTagHeritageEntity, ParseLinks) {

        $scope.textGeoTagHeritageEntitys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            TextGeoTagHeritageEntity.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.textGeoTagHeritageEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.textGeoTagHeritageEntity = {
                title: null,
                description: null,
                address: null,
                latitude: null,
                longitude: null,
                consolidatedTags: null,
                textDetails: null,
                id: null
            };
        };
    });
