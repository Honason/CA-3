//'use strict';

angular.module('myApp.view2', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/view2', {
              templateUrl: 'app/view2/view2.html',
              controller: 'View2Ctrl'
            });
          }]).filter('currencyConverter', function() {
            return function(input, toCurrency, fromCurrency) {
              return (input * (fromCurrency / toCurrency)).toFixed(2);
            };
          })

        .controller('View2Ctrl', function ($http, $scope, $window, $timeout) {

          $scope.fromCurrency = "100";
          $scope.toCurrency = "100";

            // CACHE STORE::::::::::::
            try {
                var cache = JSON.parse($window.sessionStorage.cache);
                console.log("Receiving our cache: " + cache);
                $scope.data = cache;

                $scope.fromCurrency = $scope.data.DKK + "";
                $scope.toCurrency = $scope.data.CZK + "";
                console.log($scope.data);
            } catch (err) {
                $http({
                    method: 'GET',
                    url: 'api/currency/calculator'
                }).then(function successCallback(res) {
                    console.log('Storing our cache: ' + JSON.stringify(res.data));
                    $scope.data = res.data;

                    $scope.fromCurrency = $scope.data.DKK + "";
                    $scope.toCurrency = $scope.data.CZK + "";

                    $window.sessionStorage.cache = JSON.stringify(res.data);
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });

            }
            // CACHE STORE END::::::::::::::

            $scope.checkSelect = function(key) {
                if (key === 'DKK') {
                    console.log(key);
                    return true;
                }
                 return false;

            };
        })

function round2Fixed(value) {
  value = +value;

  if (isNaN(value))
    return NaN;

  // Shift
  value = value.toString().split('e');
  value = Math.round(+(value[0] + 'e' + (value[1] ? (+value[1] + 2) : 2)));

  // Shift back
  value = value.toString().split('e');
  return (+(value[0] + 'e' + (value[1] ? (+value[1] - 2) : -2))).toFixed(2);
}
