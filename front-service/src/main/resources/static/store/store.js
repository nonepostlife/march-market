angular.module('market').controller('storeController', function ($rootScope, $scope, $http, $localStorage) {
    $scope.loadProducts = function (page = 1) {
        $http({
            url: 'http://localhost:5555/core/api/v1/products',
            method: 'GET',
            params: {
                page: page,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.products = response.data.items;
            $scope.currentPage = response.data.page + 1;
            $scope.totalPages = response.data.totalPages;
            $scope.pageSize = response.data.pageSize;
            $scope.numberOfElements = response.data.numberOfElements;
            $scope.totalElements = response.data.totalElements;
            $scope.pageNumbers = range(1, $scope.totalPages);
        });
        if (page === 1)
            document.getElementById("prev").classList.add("disabled")
        else
            document.getElementById("prev").classList.remove("disabled")
        if (page === ($scope.totalPages))
            document.getElementById("next").classList.add("disabled")
        else
            document.getElementById("next").classList.remove("disabled")

        $http({
            url: 'http://localhost:5555/core/api/v1/categories',
            method: 'GET'
        }).then(function (response) {
            console.log(response.data)
        });
    };

    $scope.addProductToCart = function (id) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId + '/add/' + id)
            .then(function (response) {
            });
    }

    $scope.resetFilters = function () {
        $scope.filter = null;
        $scope.loadProducts();
    }

    $scope.loadProducts();

    function range(start, end) {
        return Array(end - start + 1).fill().map((_, idx) => start + idx)
    }
});