'use strict';

describe('Controller Tests', function() {

    describe('HeritageLanguage Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageLanguage = jasmine.createSpy('MockHeritageLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageLanguage': MockHeritageLanguage
            };
            createController = function() {
                $injector.get('$controller')("HeritageLanguageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritageMapperAppApp:heritageLanguageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
