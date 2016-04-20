'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageWalk', {
                parent: 'entity',
                url: '/heritageWalks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageWalk.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageWalk/heritageWalks.html',
                        controller: 'HeritageWalkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageWalk');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageWalk.detail', {
                parent: 'entity',
                url: '/heritageWalk/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageWalk.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageWalk/heritageWalk-detail.html',
                        controller: 'HeritageWalkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageWalk');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageWalk', function($stateParams, HeritageWalk) {
                        return HeritageWalk.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageWalk.new', {
                parent: 'heritageWalk',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageWalk/heritageWalk-dialog.html',
                        controller: 'HeritageWalkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    background: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageWalk', null, { reload: true });
                    }, function() {
                        $state.go('heritageWalk');
                    })
                }]
            })
            .state('heritageWalk.edit', {
                parent: 'heritageWalk',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageWalk/heritageWalk-dialog.html',
                        controller: 'HeritageWalkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageWalk', function(HeritageWalk) {
                                return HeritageWalk.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageWalk', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageWalk.delete', {
                parent: 'heritageWalk',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageWalk/heritageWalk-delete-dialog.html',
                        controller: 'HeritageWalkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageWalk', function(HeritageWalk) {
                                return HeritageWalk.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageWalk', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
