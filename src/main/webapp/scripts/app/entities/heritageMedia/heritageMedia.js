'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageMedia', {
                parent: 'entity',
                url: '/heritageMedias',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageMedia.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageMedia/heritageMedias.html',
                        controller: 'HeritageMediaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageMedia');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageMedia.detail', {
                parent: 'entity',
                url: '/heritageMedia/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageMedia.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageMedia/heritageMedia-detail.html',
                        controller: 'HeritageMediaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageMedia');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageMedia', function($stateParams, HeritageMedia) {
                        return HeritageMedia.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageMedia.new', {
                parent: 'heritageMedia',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageMedia/heritageMedia-dialog.html',
                        controller: 'HeritageMediaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageMedia', null, { reload: true });
                    }, function() {
                        $state.go('heritageMedia');
                    })
                }]
            })
            .state('heritageMedia.edit', {
                parent: 'heritageMedia',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageMedia/heritageMedia-dialog.html',
                        controller: 'HeritageMediaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageMedia', function(HeritageMedia) {
                                return HeritageMedia.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageMedia', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageMedia.delete', {
                parent: 'heritageMedia',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageMedia/heritageMedia-delete-dialog.html',
                        controller: 'HeritageMediaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageMedia', function(HeritageMedia) {
                                return HeritageMedia.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageMedia', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
