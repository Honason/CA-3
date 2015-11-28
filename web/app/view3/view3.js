'use strict';

angular.module('myApp.view3', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view3', {
    templateUrl: 'app/view3/view3.html',
    controller: 'View3Ctrl'
  });
}])

.controller('View3Ctrl', function($http,$scope) {
            $scope.init = function () {
                $http({
                    method: 'GET',
                    url: 'api/admin/users'
                }).then(function successCallback(res) {
                    console.log(res.data);
                    $scope.users = res.data;
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            };
            
            $scope.init();
            
            $scope.deleteUser = function(id) {
                console.log("Trying to delete: " + id);
                    $http({
                    method: 'DELETE',
                    url: 'api/admin/users/' + id
                }).success(function() {
                    $scope.init();
                });
            };
    
 
});