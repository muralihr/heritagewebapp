'use strict';

describe('Controller Tests', function() {

    describe('HeritageSlide Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageSlide, MockHeritageMedia, MockHeritageWalk, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageSlide = jasmine.createSpy('MockHeritageSlide');
            MockHeritageMedia = jasmine.createSpy('MockHeritageMedia');
            MockHeritageWalk = jasmine.createSpy('MockHeritageWalk');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageSlide': MockHeritageSlide,
                'HeritageMedia': MockHeritageMedia,
                'HeritageWalk': MockHeritageWalk,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("HeritageSlideDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritagemapperappApp:heritageSlideUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
