'use strict';

describe('Controller Tests', function() {

    describe('HeritageRegionName Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageRegionName;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageRegionName = jasmine.createSpy('MockHeritageRegionName');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageRegionName': MockHeritageRegionName
            };
            createController = function() {
                $injector.get('$controller')("HeritageRegionNameDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritagemapperappApp:heritageRegionNameUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
