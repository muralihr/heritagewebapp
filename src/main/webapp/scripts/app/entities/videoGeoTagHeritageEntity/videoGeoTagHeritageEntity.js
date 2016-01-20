'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('videoGeoTagHeritageEntity', {
                parent: 'entity',
                url: '/videoGeoTagHeritageEntitys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.videoGeoTagHeritageEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/videoGeoTagHeritageEntity/videoGeoTagHeritageEntitys.html',
                        controller: 'VideoGeoTagHeritageEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('videoGeoTagHeritageEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('videoGeoTagHeritageEntity.detail', {
                parent: 'entity',
                url: '/videoGeoTagHeritageEntity/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.videoGeoTagHeritageEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/videoGeoTagHeritageEntity/videoGeoTagHeritageEntity-detail.html',
                        controller: 'VideoGeoTagHeritageEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('videoGeoTagHeritageEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VideoGeoTagHeritageEntity', function($stateParams, VideoGeoTagHeritageEntity) {
                        return VideoGeoTagHeritageEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('videoGeoTagHeritageEntity.new', {
                parent: 'videoGeoTagHeritageEntity',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/videoGeoTagHeritageEntity/videoGeoTagHeritageEntity-dialog.html',
                        controller: 'VideoGeoTagHeritageEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    description: null,
                                    address: null,
                                    latitude: null,
                                    longitude: null,
                                    consolidatedTags: null,
                                    urlOrfileLink: null,
                                    videoFile: null,
                                    videoFileContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('videoGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('videoGeoTagHeritageEntity');
                    })
                }]
            })
            .state('videoGeoTagHeritageEntity.edit', {
                parent: 'videoGeoTagHeritageEntity',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/videoGeoTagHeritageEntity/videoGeoTagHeritageEntity-dialog.html',
                        controller: 'VideoGeoTagHeritageEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['VideoGeoTagHeritageEntity', function(VideoGeoTagHeritageEntity) {
                                return VideoGeoTagHeritageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('videoGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('videoGeoTagHeritageEntity.delete', {
                parent: 'videoGeoTagHeritageEntity',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/videoGeoTagHeritageEntity/videoGeoTagHeritageEntity-delete-dialog.html',
                        controller: 'VideoGeoTagHeritageEntityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VideoGeoTagHeritageEntity', function(VideoGeoTagHeritageEntity) {
                                return VideoGeoTagHeritageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('videoGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
