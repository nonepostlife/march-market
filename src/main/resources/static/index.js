angular.module('market', []).controller('indexController', function ($scope, $http) {
    $scope.fillTable = function () {
        $http.get('http://localhost:8189/market/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
                // console.log(response);
            });
    };

    $scope.fillCart = function () {
        $http.get('http://localhost:8189/market/api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.addProductToCart = function (id) {
        $http.get('http://localhost:8189/market/api/v1/cart/add/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.clearCart = function () {
        $http.get('http://localhost:8189/market/api/v1/cart/clear/')
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.deleteProductFromCart = function (id) {
        $http.get('http://localhost:8189/market/api/v1/cart/delete/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.removeItemFromCart = function (id) {
        $http.get('http://localhost:8189/market/api/v1/cart/remove/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.deleteProduct = function (id) {
        $http.delete('http://localhost:8189/market/api/v1/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $scope.createNewProduct = function () {
        // console.log($scope.newProduct);
        $http.post('http://localhost:8189/market/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    }

    $scope.getCategories = function () {
        $http.get('http://localhost:8189/market/api/v1/categories')
            .then(function (response) {
                $scope.categories = response.data;
            });
    };

    $scope.getCategories();
    $scope.fillTable();
    $scope.fillCart();
});