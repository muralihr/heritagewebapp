'use strict';

describe('Controller Tests', function() {

    describe('AudioGeoTagHeritageEntity Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAudioGeoTagHeritageEntity, MockHeritageCategory, MockHeritageLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAudioGeoTagHeritageEntity = jasmine.createSpy('MockAudioGeoTagHeritageEntity');
            MockHeritageCategory = jasmine.createSpy('MockHeritageCategory');
            MockHeritageLanguage = jasmine.createSpy('MockHeritageLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AudioGeoTagHeritageEntity': MockAudioGeoTagHeritageEntity,
                'HeritageCategory': MockHeritageCategory,
                'HeritageLanguage': MockHeritageLanguage
            };
            createController = function() {
                $injector.get('$controller')("AudioGeoTagHeritageEntityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritageMapperAppApp:audioGeoTagHeritageEntityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
