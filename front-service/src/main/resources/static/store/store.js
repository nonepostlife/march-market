angular.module('market').controller('storeController', function ($rootScope, $scope, $http, $localStorage) {
    $scope.loadProducts = function (page) {
        $http.get('http://localhost:5555/core/api/v1/products?page=' + (page))
            .then(function (response) {
                $scope.products = response.data.content;
                $scope.currentPage = response.data.number;
                $scope.totalPages = response.data.totalPages
                $scope.pageNumbers = range(0, $scope.totalPages - 1)
            });
        if (page === 0)
            document.getElementById("prev").classList.add("disabled")
        else
            document.getElementById("prev").classList.remove("disabled")
        if (page === ($scope.totalPages - 1))
            document.getElementById("next").classList.add("disabled")
        else
            document.getElementById("next").classList.remove("disabled")
    };

    $scope.addProductToCart = function (id) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId + '/add/' + id)
            .then(function (response) {
            });
    }

    $scope.loadProducts(0);

    function range(start, end) {
        return Array(end - start + 1).fill().map((_, idx) => start + idx)
    }
});