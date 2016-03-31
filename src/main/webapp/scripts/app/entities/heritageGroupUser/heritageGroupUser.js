'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageGroupUser', {
                parent: 'entity',
                url: '/heritageGroupUsers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageGroupUser.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageGroupUser/heritageGroupUsers.html',
                        controller: 'HeritageGroupUserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageGroupUser');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageGroupUser.detail', {
                parent: 'entity',
                url: '/heritageGroupUser/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageGroupUser.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageGroupUser/heritageGroupUser-detail.html',
                        controller: 'HeritageGroupUserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageGroupUser');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageGroupUser', function($stateParams, HeritageGroupUser) {
                        return HeritageGroupUser.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageGroupUser.new', {
                parent: 'heritageGroupUser',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroupUser/heritageGroupUser-dialog.html',
                        controller: 'HeritageGroupUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    reason: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroupUser', null, { reload: true });
                    }, function() {
                        $state.go('heritageGroupUser');
                    })
                }]
            })
            .state('heritageGroupUser.edit', {
                parent: 'heritageGroupUser',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroupUser/heritageGroupUser-dialog.html',
                        controller: 'HeritageGroupUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageGroupUser', function(HeritageGroupUser) {
                                return HeritageGroupUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroupUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageGroupUser.delete', {
                parent: 'heritageGroupUser',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroupUser/heritageGroupUser-delete-dialog.html',
                        controller: 'HeritageGroupUserDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageGroupUser', function(HeritageGroupUser) {
                                return HeritageGroupUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroupUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
