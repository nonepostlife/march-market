angular.module('market').controller('cartController', function ($rootScope, $scope, $http, $localStorage) {
    $rootScope.loadCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId)
            .then(function (response) {
                $scope.cart = response.data;
            });
    };
    $rootScope.addProductToCart = function (id) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId + '/add/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.clearCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId + '/clear/')
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.deleteProductFromCart = function (id) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId + '/delete/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.removeItemFromCart = function (id) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId + '/remove/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.createOrder = function () {
        if ($scope.address.country == null || $scope.address.zip == null || $scope.address.city == null ||
            $scope.address.address == null || $scope.address.phone == null) {

        } else {
            $http.post('http://localhost:5555/core/api/v1/orders', $scope.address)
                .then(function (response) {
                    $scope.loadCart();
                    $scope.address = null;
                });
        }
    }

    $scope.guestCreateOrder = function () {
        alert('Для оформления заказа необходимо войти в учетную запись');
    }

    $scope.loadCart();
});