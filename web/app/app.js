//'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'ui.bootstrap',
  'myApp.security',
  'myApp.view1',
  'myApp.opendata',
  'myApp.view3',
  'myApp.view2',
  'myApp.view4',
  'myApp.register',
  'myApp.documentation',
  'myApp.filters',
  'myApp.controllers',
  'myApp.directives',
  'myApp.factories',
  'myApp.services'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/view1'});
}]).
config(function ($httpProvider) {
   $httpProvider.interceptors.push('authInterceptor');
});
