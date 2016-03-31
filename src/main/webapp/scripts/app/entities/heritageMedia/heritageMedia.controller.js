'use strict';

angular.module('heritageMapperAppApp')
    .controller('HeritageMediaController', function ($scope, $state, DataUtils, HeritageMedia, ParseLinks) {

        $scope.heritageMedias = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HeritageMedia.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.heritageMedias = result;
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
            $scope.heritageMedia = {
                title: null,
                description: null,
                address: null,
                latitude: null,
                longitude: null,
                mediaType: null,
                mediaFile: null,
                mediaFileContentType: null,
                urlOrfileLink: null,
                consolidatedTags: null,
                userAgent: null,
                uploadTime: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });
