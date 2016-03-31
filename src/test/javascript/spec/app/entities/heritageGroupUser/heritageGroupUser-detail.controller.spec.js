'use strict';

describe('Controller Tests', function() {

    describe('HeritageGroupUser Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageGroupUser, MockHeritageGroup, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageGroupUser = jasmine.createSpy('MockHeritageGroupUser');
            MockHeritageGroup = jasmine.createSpy('MockHeritageGroup');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageGroupUser': MockHeritageGroupUser,
                'HeritageGroup': MockHeritageGroup,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("HeritageGroupUserDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritagemapperappApp:heritageGroupUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
