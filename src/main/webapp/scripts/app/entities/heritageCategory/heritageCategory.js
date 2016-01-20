'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageCategory', {
                parent: 'entity',
                url: '/heritageCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.heritageCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageCategory/heritageCategorys.html',
                        controller: 'HeritageCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageCategory.detail', {
                parent: 'entity',
                url: '/heritageCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.heritageCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageCategory/heritageCategory-detail.html',
                        controller: 'HeritageCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageCategory', function($stateParams, HeritageCategory) {
                        return HeritageCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageCategory.new', {
                parent: 'heritageCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageCategory/heritageCategory-dialog.html',
                        controller: 'HeritageCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    categoryName: null,
                                    categoryIcon: null,
                                    categoryIconContentType: null,
                                    categoryDecription: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageCategory', null, { reload: true });
                    }, function() {
                        $state.go('heritageCategory');
                    })
                }]
            })
            .state('heritageCategory.edit', {
                parent: 'heritageCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageCategory/heritageCategory-dialog.html',
                        controller: 'HeritageCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageCategory', function(HeritageCategory) {
                                return HeritageCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageCategory.delete', {
                parent: 'heritageCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageCategory/heritageCategory-delete-dialog.html',
                        controller: 'HeritageCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageCategory', function(HeritageCategory) {
                                return HeritageCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
