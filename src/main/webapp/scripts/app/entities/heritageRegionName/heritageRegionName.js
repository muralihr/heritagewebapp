'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageRegionName', {
                parent: 'entity',
                url: '/heritageRegionNames',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageRegionName.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageRegionName/heritageRegionNames.html',
                        controller: 'HeritageRegionNameController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageRegionName');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageRegionName.detail', {
                parent: 'entity',
                url: '/heritageRegionName/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageRegionName.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageRegionName/heritageRegionName-detail.html',
                        controller: 'HeritageRegionNameDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageRegionName');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageRegionName', function($stateParams, HeritageRegionName) {
                        return HeritageRegionName.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageRegionName.new', {
                parent: 'heritageRegionName',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageRegionName/heritageRegionName-dialog.html',
                        controller: 'HeritageRegionNameDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    centerLatitude: null,
                                    centerLongitude: null,
                                    topLatitude: null,
                                    topLongitude: null,
                                    bottomLatitude: null,
                                    bottomLongitude: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageRegionName', null, { reload: true });
                    }, function() {
                        $state.go('heritageRegionName');
                    })
                }]
            })
            .state('heritageRegionName.edit', {
                parent: 'heritageRegionName',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageRegionName/heritageRegionName-dialog.html',
                        controller: 'HeritageRegionNameDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageRegionName', function(HeritageRegionName) {
                                return HeritageRegionName.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageRegionName', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageRegionName.delete', {
                parent: 'heritageRegionName',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageRegionName/heritageRegionName-delete-dialog.html',
                        controller: 'HeritageRegionNameDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageRegionName', function(HeritageRegionName) {
                                return HeritageRegionName.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageRegionName', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
