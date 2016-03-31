'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageGroup', {
                parent: 'entity',
                url: '/heritageGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageGroup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageGroup/heritageGroups.html',
                        controller: 'HeritageGroupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageGroup.detail', {
                parent: 'entity',
                url: '/heritageGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageGroup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageGroup/heritageGroup-detail.html',
                        controller: 'HeritageGroupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageGroup', function($stateParams, HeritageGroup) {
                        return HeritageGroup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageGroup.new', {
                parent: 'heritageGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroup/heritageGroup-dialog.html',
                        controller: 'HeritageGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    icon: null,
                                    iconContentType: null,
                                    details: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroup', null, { reload: true });
                    }, function() {
                        $state.go('heritageGroup');
                    })
                }]
            })
            .state('heritageGroup.edit', {
                parent: 'heritageGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroup/heritageGroup-dialog.html',
                        controller: 'HeritageGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageGroup', function(HeritageGroup) {
                                return HeritageGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageGroup.delete', {
                parent: 'heritageGroup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroup/heritageGroup-delete-dialog.html',
                        controller: 'HeritageGroupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageGroup', function(HeritageGroup) {
                                return HeritageGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
