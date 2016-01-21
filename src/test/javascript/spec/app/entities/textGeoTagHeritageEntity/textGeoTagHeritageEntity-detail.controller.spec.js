'use strict';

describe('Controller Tests', function() {

    describe('TextGeoTagHeritageEntity Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTextGeoTagHeritageEntity, MockHeritageCategory, MockHeritageLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTextGeoTagHeritageEntity = jasmine.createSpy('MockTextGeoTagHeritageEntity');
            MockHeritageCategory = jasmine.createSpy('MockHeritageCategory');
            MockHeritageLanguage = jasmine.createSpy('MockHeritageLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TextGeoTagHeritageEntity': MockTextGeoTagHeritageEntity,
                'HeritageCategory': MockHeritageCategory,
                'HeritageLanguage': MockHeritageLanguage
            };
            createController = function() {
                $injector.get('$controller')("TextGeoTagHeritageEntityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritageMapperAppApp:textGeoTagHeritageEntityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
