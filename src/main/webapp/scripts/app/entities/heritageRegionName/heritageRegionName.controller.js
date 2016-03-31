'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageRegionNameController', function ($scope, $state, HeritageRegionName, ParseLinks) {

        $scope.heritageRegionNames = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HeritageRegionName.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.heritageRegionNames = result;
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
            $scope.heritageRegionName = {
                name: null,
                centerLatitude: null,
                centerLongitude: null,
                topLatitude: null,
                topLongitude: null,
                bottomLatitude: null,
                bottomLongitude: null,
                id: null
            };
        };
    });
