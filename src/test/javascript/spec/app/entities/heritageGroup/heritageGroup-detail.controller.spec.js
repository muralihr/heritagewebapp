'use strict';

describe('Controller Tests', function() {

    describe('HeritageGroup Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageGroup = jasmine.createSpy('MockHeritageGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageGroup': MockHeritageGroup
            };
            createController = function() {
                $injector.get('$controller')("HeritageGroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritagemapperappApp:heritageGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
