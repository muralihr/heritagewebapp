'use strict';

describe('Controller Tests', function() {

    describe('HeritageApp Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageApp, MockHeritageRegionName, MockHeritageGroup, MockHeritageLanguage, MockHeritageCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageApp = jasmine.createSpy('MockHeritageApp');
            MockHeritageRegionName = jasmine.createSpy('MockHeritageRegionName');
            MockHeritageGroup = jasmine.createSpy('MockHeritageGroup');
            MockHeritageLanguage = jasmine.createSpy('MockHeritageLanguage');
            MockHeritageCategory = jasmine.createSpy('MockHeritageCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageApp': MockHeritageApp,
                'HeritageRegionName': MockHeritageRegionName,
                'HeritageGroup': MockHeritageGroup,
                'HeritageLanguage': MockHeritageLanguage,
                'HeritageCategory': MockHeritageCategory
            };
            createController = function() {
                $injector.get('$controller')("HeritageAppDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritagemapperappApp:heritageAppUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
