//'use strict';

angular.module('myApp.opendata', ['ngRoute'])

.config(['$routeProvider', function ($routeProvider) {
  $routeProvider.when('/opendata', {
    templateUrl: 'app/opendata/opendata.html',
    controller: 'opendataCtrl'
  });
}])

.controller('opendataCtrl', function ($http, $scope) {
  var apiUrl = "http://cvrapi.dk/api";

  $scope.searchScope = "search";
  $scope.country = "dk";

  $scope.searchData = function() {
    // console.log($scope.searchText);
    // console.log($scope.searchScope);
    // console.log($scope.country);

    if (!$scope.searchText || $scope.searchText === "") {
      return;
    }

    $http({
      method: 'GET',
      url: apiUrl,
      params: {search: $scope.searchText, country: $scope.country}
    }).then(function successCallback(res) {
      if (res.status === 404) {
        $scope.dataLoaded = false;
        $scope.displayNotFound = true;
        return;
      }
      $scope.dataLoaded = true;
      $scope.displayNotFound = false;
      $scope.data = res.data;
      console.log(res);
      //$scope.data = res.data.message;
    }, function errorCallback(res) {
      console.log("error");
      //$scope.error = res.status + ": "+ res.data.statusText;
    });
  };


  $scope.oneAtATime = true;

  $scope.status = {
    isFirstOpen: true,
    isFirstDisabled: false
  };


});
