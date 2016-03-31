'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageGroupEditor', {
                parent: 'entity',
                url: '/heritageGroupEditors',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageGroupEditor.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageGroupEditor/heritageGroupEditors.html',
                        controller: 'HeritageGroupEditorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageGroupEditor');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageGroupEditor.detail', {
                parent: 'entity',
                url: '/heritageGroupEditor/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageGroupEditor.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageGroupEditor/heritageGroupEditor-detail.html',
                        controller: 'HeritageGroupEditorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageGroupEditor');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageGroupEditor', function($stateParams, HeritageGroupEditor) {
                        return HeritageGroupEditor.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageGroupEditor.new', {
                parent: 'heritageGroupEditor',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroupEditor/heritageGroupEditor-dialog.html',
                        controller: 'HeritageGroupEditorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    about: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroupEditor', null, { reload: true });
                    }, function() {
                        $state.go('heritageGroupEditor');
                    })
                }]
            })
            .state('heritageGroupEditor.edit', {
                parent: 'heritageGroupEditor',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroupEditor/heritageGroupEditor-dialog.html',
                        controller: 'HeritageGroupEditorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageGroupEditor', function(HeritageGroupEditor) {
                                return HeritageGroupEditor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroupEditor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageGroupEditor.delete', {
                parent: 'heritageGroupEditor',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageGroupEditor/heritageGroupEditor-delete-dialog.html',
                        controller: 'HeritageGroupEditorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageGroupEditor', function(HeritageGroupEditor) {
                                return HeritageGroupEditor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageGroupEditor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
