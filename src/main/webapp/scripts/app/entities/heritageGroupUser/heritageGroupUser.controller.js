'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageGroupUserController', function ($scope, $state, HeritageGroupUser, ParseLinks) {

        $scope.heritageGroupUsers = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HeritageGroupUser.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.heritageGroupUsers = result;
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
            $scope.heritageGroupUser = {
                reason: null,
                status: null,
                id: null
            };
        };
    });
