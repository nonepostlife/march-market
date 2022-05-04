angular.module('market').controller('authController', function ($rootScope, $scope, $http, $location, $localStorage) {
    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marchMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $scope.mergeCart();

                    $location.path('/');
                }
            }, function errorCallback(response) {
            });
    };

    $scope.mergeCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId + '/merge')
            .then(function (response) {
                console.log("merge complete")
            });
    }
});