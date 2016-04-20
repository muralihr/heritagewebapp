'use strict';

describe('Controller Tests', function() {

    describe('HeritageWalk Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageWalk, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageWalk = jasmine.createSpy('MockHeritageWalk');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageWalk': MockHeritageWalk,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("HeritageWalkDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritagemapperappApp:heritageWalkUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
