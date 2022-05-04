angular.module('market').controller('registerController', function ($rootScope, $scope, $http, $location) {
    $scope.registerNewUser = function () {
        $http.post('http://localhost:5555/auth/register', $scope.user)
            .then(function successCallback(response) {
                alert(response.data);

                $location.path('/auth');
            }, function errorCallback(response) {
            });
    };

});