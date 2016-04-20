'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heritageSlide', {
                parent: 'entity',
                url: '/heritageSlides',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageSlide.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageSlide/heritageSlides.html',
                        controller: 'HeritageSlideController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageSlide');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageSlideCreateWalk', {
                parent: 'entity',
                url: '/heritageSlidesCreateWalk',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageSlide.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageSlide/heritageSlidesCreateWalk.html',
                        controller: 'HeritageSlideController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageSlide');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
             .state('heritagePreviewWalk', {
                parent: 'entity',
                url: '/heritageSlidesPreviewWalk',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageSlide.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageSlide/heritageSlidesPreviewWalk.html',
                        controller: 'HeritageSlidePreviewController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageSlide');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
             .state('simple', {
                parent: 'entity',
                url: '/simple',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageSlide.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageSlide/simple-frame.html',
                        controller: 'SimpleDemoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageSlide');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heritageSlide.detail', {
                parent: 'entity',
                url: '/heritageSlide/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'heritagemapperappApp.heritageSlide.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heritageSlide/heritageSlide-detail.html',
                        controller: 'HeritageSlideDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heritageSlide');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeritageSlide', function($stateParams, HeritageSlide) {
                        return HeritageSlide.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heritageSlide.new', {
                parent: 'heritageSlide',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageSlide/heritageSlide-dialog.html',
                        controller: 'HeritageSlideDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    indexVal: null,
                                    notes: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heritageSlide', null, { reload: true });
                    }, function() {
                        $state.go('heritageSlide');
                    })
                }]
            })
            .state('heritageSlide.edit', {
                parent: 'heritageSlide',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageSlide/heritageSlide-dialog.html',
                        controller: 'HeritageSlideDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeritageSlide', function(HeritageSlide) {
                                return HeritageSlide.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageSlide', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heritageSlide.delete', {
                parent: 'heritageSlide',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heritageSlide/heritageSlide-delete-dialog.html',
                        controller: 'HeritageSlideDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeritageSlide', function(HeritageSlide) {
                                return HeritageSlide.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heritageSlide', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
