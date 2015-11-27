//'use strict';

angular.module('myApp.register', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/register', {
    templateUrl: 'app/register/register.html'
  });
}])

.controller('registerController', ["InfoFactory","InfoService","$http","$window","$location","loginService",function(InfoFactory,InfoService,$http,$window,$location,loginService) {
  var self = this;
  this.loginService = loginService;

  function url_base64_decode(str) {
    var output = str.replace('-', '+').replace('_', '/');
    switch (output.length % 4) {
      case 0:
        break;
      case 2:
        output += '==';
        break;
      case 3:
        output += '=';
        break;
      default:
        throw 'Illegal base64url string!';
    }
    return window.atob(output); //polifyll https://github.com/davidchambers/Base64.js
  }

  this.register = function () {
    $http
            .post('api/register', self.user)
            .success(function (data, status, headers, config) {
              $window.sessionStorage.token = data.token;
              loginService.isAuthenticated = true;
              var encodedProfile = data.token.split('.')[1];
              var profile = JSON.parse(url_base64_decode(encodedProfile));
              loginService.username = profile.username;
              var roles = profile.roles.split(",");
              roles.forEach(function (role) {
                if(role === "Admin"){
                   loginService.isAdmin =true;
                 }
                if(role === "User"){
                   loginService.isUser = true;
                 }
              });
              self.error = null;
              $location.path("#/view1");
            })
            .error(function (data, status, headers, config) {
              // Erase the token if the user fails to log in
              delete $window.sessionStorage.token;
              loginService.isAuthenticated = true;
              loginService.isAdmin = false;
              loginService.isUser = false;
              loginService.username = "";
              self.error = data.error;
              //self.logout();  //Clears an eventual error message from timeout on the inner view
            });
  };
}]);
