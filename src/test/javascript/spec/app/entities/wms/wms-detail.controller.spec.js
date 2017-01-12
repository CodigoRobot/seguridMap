'use strict';

describe('Controller Tests', function() {

    describe('Wms Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWms, MockPanel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWms = jasmine.createSpy('MockWms');
            MockPanel = jasmine.createSpy('MockPanel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Wms': MockWms,
                'Panel': MockPanel
            };
            createController = function() {
                $injector.get('$controller')("WmsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'seguridMapApp:wmsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
