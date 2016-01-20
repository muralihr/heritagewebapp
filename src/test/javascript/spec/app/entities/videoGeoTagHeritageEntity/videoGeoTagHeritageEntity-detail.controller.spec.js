'use strict';

describe('Controller Tests', function() {

    describe('VideoGeoTagHeritageEntity Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVideoGeoTagHeritageEntity, MockHeritageCategory, MockHeritageLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVideoGeoTagHeritageEntity = jasmine.createSpy('MockVideoGeoTagHeritageEntity');
            MockHeritageCategory = jasmine.createSpy('MockHeritageCategory');
            MockHeritageLanguage = jasmine.createSpy('MockHeritageLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'VideoGeoTagHeritageEntity': MockVideoGeoTagHeritageEntity,
                'HeritageCategory': MockHeritageCategory,
                'HeritageLanguage': MockHeritageLanguage
            };
            createController = function() {
                $injector.get('$controller')("VideoGeoTagHeritageEntityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritageMapperAppApp:videoGeoTagHeritageEntityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
