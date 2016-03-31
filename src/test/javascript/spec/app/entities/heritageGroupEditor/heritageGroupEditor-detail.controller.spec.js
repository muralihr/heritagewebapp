'use strict';

describe('Controller Tests', function() {

    describe('HeritageGroupEditor Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHeritageGroupEditor, MockHeritageGroup, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHeritageGroupEditor = jasmine.createSpy('MockHeritageGroupEditor');
            MockHeritageGroup = jasmine.createSpy('MockHeritageGroup');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HeritageGroupEditor': MockHeritageGroupEditor,
                'HeritageGroup': MockHeritageGroup,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("HeritageGroupEditorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'heritagemapperappApp:heritageGroupEditorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
