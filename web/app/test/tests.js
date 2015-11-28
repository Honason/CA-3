
describe('Unit: opendataCtrl', function() {
  // Load the module with MainController
  beforeEach(module('myApp'));

  var ctrl, scope;
  // inject the $controller and $rootScope services
  // in the beforeEach block
  beforeEach(inject(function($controller, $rootScope) {
    // Create a new scope that's a child of the $rootScope
    scope = $rootScope.$new();
    // Create the controller
    ctrl = $controller('opendataCtrl', {
      $scope: scope
    });
  }));

  it('should respond with null because no search query is defined', 
    function() {
      expect(scope.searchData()).toBeUndefined();
  });

  it('searching with the query "hest" should correctly respond with an object containing "hest" as the name', 
    function() {
      scope.searchText = 'hest'
      scope.searchData()
      setTimeout(function() {
        expect(scope.data.name.toLowerCase()).toContain('hest');
      }, 1700);
  });

})

describe('Unit: AppLoginCtrl', function() {
  // Load the module with MainController
  beforeEach(module('myApp'));

  var ctrl, scope, wind, root;
  // inject the $controller and $rootScope services
  // in the beforeEach block
  beforeEach(inject(function($controller, $rootScope, $window) {
    // Create a new scope that's a child of the $rootScope
    root = $rootScope;
    scope = $rootScope.$new();
    wind = $window;
    // Create the controller
    ctrl = $controller('AppLoginCtrl', {
      $scope: scope,
      $window: wind
    });
  }));


  it('logout function should remove the token', 
    function() {
      wind.sessionStorage.token = "validToken";
      expect(wind.sessionStorage.token).toBeDefined();
      root.logout()
      expect(wind.sessionStorage.token).toBeUndefined();
  });


})


  describe('myApp', function () {

    beforeEach(function () {
      module('myApp.view2');
    });


    it("should convert between currencies", inject(function (currencyConverterFilter) {
      expect(currencyConverterFilter(1,1,2)).toBe('2.00');
    }));

    it("should return only 2 decimals ", inject(function (currencyConverterFilter) {
      expect(currencyConverterFilter(1,7,22)).toBe('3.14');
    }));

  });
