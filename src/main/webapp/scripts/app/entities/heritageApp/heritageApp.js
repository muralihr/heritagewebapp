'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageApp', {
                parent: 'entity',
                url: '/heritageApps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageApp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageApp/heritageApps.html',
                        controller: 'HeritageAppController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageApp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageApp.detail', {
                parent: 'entity',
                url: '/heritageApp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageApp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageApp/heritageApp-detail.html',
                        controller: 'HeritageAppDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageApp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageApp', function($stateParams, HeritageApp) {
                        return HeritageApp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageApp.new', {
                parent: 'heritageApp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageApp/heritageApp-dialog.html',
                        controller: 'HeritageAppDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    contact: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageApp', null, { reload: true });
                    }, function() {
                        $state.go('heritageApp');
                    })
                }]
            })
            .state('heritageApp.edit', {
                parent: 'heritageApp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageApp/heritageApp-dialog.html',
                        controller: 'HeritageAppDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageApp', function(HeritageApp) {
                                return HeritageApp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageApp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageApp.delete', {
                parent: 'heritageApp',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageApp/heritageApp-delete-dialog.html',
                        controller: 'HeritageAppDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageApp', function(HeritageApp) {
                                return HeritageApp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageApp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
