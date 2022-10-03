angular.module('market').controller('ordersController', function ($scope, $http, $location) {
    $scope.loadOrders = function (page = 1) {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders',
            method: 'GET',
            params: {
                page: page
            }
        }).then(function (response) {
            $scope.orders = response.data.items;
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
    };

    $scope.goToPay = function (orderId) {
        $location.path('/order_pay/' + orderId);
    }

    $scope.confirmReceiptOrder = function (orderId) {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders/receive/' + orderId,
            method: 'GET',

        }).then(function (response) {
            $scope.loadOrders($scope.currentPage);
        });
    }


    $scope.loadOrders();

    function range(start, end) {
        return Array(end - start + 1).fill().map((_, idx) => start + idx)
    }
});