'use strict';

angular.module('heritageMapperAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('groups', {
                parent: 'account',
                url: '/groups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'global.menu.account.groups'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/groups/groups.html',
                        controller: 'GroupsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('groups');
                        return $translate.refresh();
                    }]
                }
            });
    });
