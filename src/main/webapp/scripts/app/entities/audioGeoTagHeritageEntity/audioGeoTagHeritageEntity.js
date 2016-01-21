'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('audioGeoTagHeritageEntity', {
                parent: 'entity',
                url: '/audioGeoTagHeritageEntitys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.audioGeoTagHeritageEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/audioGeoTagHeritageEntity/audioGeoTagHeritageEntitys.html',
                        controller: 'AudioGeoTagHeritageEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('audioGeoTagHeritageEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('audioGeoTagHeritageEntity.detail', {
                parent: 'entity',
                url: '/audioGeoTagHeritageEntity/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.audioGeoTagHeritageEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/audioGeoTagHeritageEntity/audioGeoTagHeritageEntity-detail.html',
                        controller: 'AudioGeoTagHeritageEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('audioGeoTagHeritageEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AudioGeoTagHeritageEntity', function($stateParams, AudioGeoTagHeritageEntity) {
                        return AudioGeoTagHeritageEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('audioGeoTagHeritageEntity.new', {
                parent: 'audioGeoTagHeritageEntity',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/audioGeoTagHeritageEntity/audioGeoTagHeritageEntity-dialog.html',
                        controller: 'AudioGeoTagHeritageEntityDialogController',
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
                                    audioFile: null,
                                    audioFileContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('audioGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('audioGeoTagHeritageEntity');
                    })
                }]
            })
            .state('audioGeoTagHeritageEntity.edit', {
                parent: 'audioGeoTagHeritageEntity',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/audioGeoTagHeritageEntity/audioGeoTagHeritageEntity-dialog.html',
                        controller: 'AudioGeoTagHeritageEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AudioGeoTagHeritageEntity', function(AudioGeoTagHeritageEntity) {
                                return AudioGeoTagHeritageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('audioGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('audioGeoTagHeritageEntity.delete', {
                parent: 'audioGeoTagHeritageEntity',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/audioGeoTagHeritageEntity/audioGeoTagHeritageEntity-delete-dialog.html',
                        controller: 'AudioGeoTagHeritageEntityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AudioGeoTagHeritageEntity', function(AudioGeoTagHeritageEntity) {
                                return AudioGeoTagHeritageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('audioGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
