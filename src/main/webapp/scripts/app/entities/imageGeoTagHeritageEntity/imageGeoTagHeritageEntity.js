'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('imageGeoTagHeritageEntity', {
                parent: 'entity',
                url: '/imageGeoTagHeritageEntitys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.imageGeoTagHeritageEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/imageGeoTagHeritageEntity/imageGeoTagHeritageEntitys.html',
                        controller: 'ImageGeoTagHeritageEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('imageGeoTagHeritageEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('imageGeoTagHeritageEntity.detail', {
                parent: 'entity',
                url: '/imageGeoTagHeritageEntity/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.imageGeoTagHeritageEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/imageGeoTagHeritageEntity/imageGeoTagHeritageEntity-detail.html',
                        controller: 'ImageGeoTagHeritageEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('imageGeoTagHeritageEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ImageGeoTagHeritageEntity', function($stateParams, ImageGeoTagHeritageEntity) {
                        return ImageGeoTagHeritageEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('imageGeoTagHeritageEntity.new', {
                parent: 'imageGeoTagHeritageEntity',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/imageGeoTagHeritageEntity/imageGeoTagHeritageEntity-dialog.html',
                        controller: 'ImageGeoTagHeritageEntityDialogController',
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
                                    photo: null,
                                    photoContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('imageGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('imageGeoTagHeritageEntity');
                    })
                }]
            })
            .state('imageGeoTagHeritageEntity.edit', {
                parent: 'imageGeoTagHeritageEntity',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/imageGeoTagHeritageEntity/imageGeoTagHeritageEntity-dialog.html',
                        controller: 'ImageGeoTagHeritageEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ImageGeoTagHeritageEntity', function(ImageGeoTagHeritageEntity) {
                                return ImageGeoTagHeritageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('imageGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('imageGeoTagHeritageEntity.delete', {
                parent: 'imageGeoTagHeritageEntity',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/imageGeoTagHeritageEntity/imageGeoTagHeritageEntity-delete-dialog.html',
                        controller: 'ImageGeoTagHeritageEntityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ImageGeoTagHeritageEntity', function(ImageGeoTagHeritageEntity) {
                                return ImageGeoTagHeritageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('imageGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
