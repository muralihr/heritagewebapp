'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageLanguage', {
                parent: 'entity',
                url: '/heritageLanguages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.heritageLanguage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageLanguage/heritageLanguages.html',
                        controller: 'HeritageLanguageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageLanguage');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageLanguage.detail', {
                parent: 'entity',
                url: '/heritageLanguage/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.heritageLanguage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageLanguage/heritageLanguage-detail.html',
                        controller: 'HeritageLanguageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageLanguage');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageLanguage', function($stateParams, HeritageLanguage) {
                        return HeritageLanguage.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageLanguage.new', {
                parent: 'heritageLanguage',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageLanguage/heritageLanguage-dialog.html',
                        controller: 'HeritageLanguageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    heritageLanguage: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageLanguage', null, { reload: true });
                    }, function() {
                        $state.go('heritageLanguage');
                    })
                }]
            })
            .state('heritageLanguage.edit', {
                parent: 'heritageLanguage',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageLanguage/heritageLanguage-dialog.html',
                        controller: 'HeritageLanguageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageLanguage', function(HeritageLanguage) {
                                return HeritageLanguage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageLanguage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageLanguage.delete', {
                parent: 'heritageLanguage',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageLanguage/heritageLanguage-delete-dialog.html',
                        controller: 'HeritageLanguageDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageLanguage', function(HeritageLanguage) {
                                return HeritageLanguage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageLanguage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
