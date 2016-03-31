'use strict';

describe('Controller Tests', function() {

    describe('HeritageMedia Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageMedia, MockHeritageCategory, MockHeritageLanguage, MockHeritageGroup, MockHeritageApp, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageMedia = jasmine.createSpy('MockHeritageMedia');
            MockHeritageCategory = jasmine.createSpy('MockHeritageCategory');
            MockHeritageLanguage = jasmine.createSpy('MockHeritageLanguage');
            MockHeritageGroup = jasmine.createSpy('MockHeritageGroup');
            MockHeritageApp = jasmine.createSpy('MockHeritageApp');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageMedia': MockHeritageMedia,
                'HeritageCategory': MockHeritageCategory,
                'HeritageLanguage': MockHeritageLanguage,
                'HeritageGroup': MockHeritageGroup,
                'HeritageApp': MockHeritageApp,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("HeritageMediaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritagemapperappApp:heritageMediaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
