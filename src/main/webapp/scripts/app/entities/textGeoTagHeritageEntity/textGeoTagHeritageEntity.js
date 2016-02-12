'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('textGeoTagHeritageEntity', {
                parent: 'entity',
                url: '/textGeoTagHeritageEntitys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.textGeoTagHeritageEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/textGeoTagHeritageEntity/textGeoTagHeritageEntitys.html',
                        controller: 'TextGeoTagHeritageEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('textGeoTagHeritageEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('textGeoTagHeritageEntity.detail', {
                parent: 'entity',
                url: '/textGeoTagHeritageEntity/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritageMapperAppApp.textGeoTagHeritageEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/textGeoTagHeritageEntity/textGeoTagHeritageEntity-detail.html',
                        controller: 'TextGeoTagHeritageEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('textGeoTagHeritageEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TextGeoTagHeritageEntity', function($stateParams, TextGeoTagHeritageEntity) {
                        return TextGeoTagHeritageEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('textGeoTagHeritageEntity.new', {
                parent: 'textGeoTagHeritageEntity',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/textGeoTagHeritageEntity/textGeoTagHeritageEntity-dialog.html',
                        controller: 'TextGeoTagHeritageEntityDialogController',
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
                                    textDetails: null,
                                    uploadTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('textGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('textGeoTagHeritageEntity');
                    })
                }]
            })
            .state('textGeoTagHeritageEntity.edit', {
                parent: 'textGeoTagHeritageEntity',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/textGeoTagHeritageEntity/textGeoTagHeritageEntity-dialog.html',
                        controller: 'TextGeoTagHeritageEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TextGeoTagHeritageEntity', function(TextGeoTagHeritageEntity) {
                                return TextGeoTagHeritageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('textGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('textGeoTagHeritageEntity.delete', {
                parent: 'textGeoTagHeritageEntity',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/textGeoTagHeritageEntity/textGeoTagHeritageEntity-delete-dialog.html',
                        controller: 'TextGeoTagHeritageEntityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TextGeoTagHeritageEntity', function(TextGeoTagHeritageEntity) {
                                return TextGeoTagHeritageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('textGeoTagHeritageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
