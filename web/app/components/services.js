//'use strict';

/* Place your Global Services in this File */

// Demonstrate how to register services
angular.module('myApp.services', [])
  .service('InfoService', [function () {
    var info = "Hello World from a Service";
    this.getInfo = function(){return info;};
  }])

  .service('loginService', [function () {
    var self = this;

    this.username = "";
    this.isAuthenticated = false;
    this.isAdmin = false;
    this.isUser = false;
    this.message = '';
  }]);
