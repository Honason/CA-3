//'use strict';

angular.module('myApp.documentation', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/documentation', {
    templateUrl: 'app/documentation/documentation.html'
  });
}]);
