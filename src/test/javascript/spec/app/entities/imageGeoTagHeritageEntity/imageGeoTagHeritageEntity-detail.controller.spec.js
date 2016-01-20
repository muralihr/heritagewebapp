'use strict';

describe('Controller Tests', function() {

    describe('ImageGeoTagHeritageEntity Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockImageGeoTagHeritageEntity, MockHeritageCategory, MockHeritageLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockImageGeoTagHeritageEntity = jasmine.createSpy('MockImageGeoTagHeritageEntity');
            MockHeritageCategory = jasmine.createSpy('MockHeritageCategory');
            MockHeritageLanguage = jasmine.createSpy('MockHeritageLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ImageGeoTagHeritageEntity': MockImageGeoTagHeritageEntity,
                'HeritageCategory': MockHeritageCategory,
                'HeritageLanguage': MockHeritageLanguage
            };
            createController = function() {
                $injector.get('$controller')("ImageGeoTagHeritageEntityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritageMapperAppApp:imageGeoTagHeritageEntityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
