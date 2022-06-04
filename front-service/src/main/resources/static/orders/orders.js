angular.module('market').controller('ordersController', function ($scope, $http) {
    $scope.loadOrders = function (page = 1) {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders',
            method: 'GET',
            params: {
                page: page
            }
        }).then(function (response) {
            console.log(response.data.items)

            $scope.orders = response.data.items;
            $scope.currentPage = response.data.page + 1;
            $scope.totalPages = response.data.totalPages;
            $scope.pageSize = response.data.pageSize;
            $scope.numberOfElements = response.data.numberOfElements;
            $scope.totalElements = response.data.totalElements;
            $scope.pageNumbers = range(1, $scope.totalPages);
            console.log($scope)
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

    $scope.loadOrders();

    function range(start, end) {
        return Array(end - start + 1).fill().map((_, idx) => start + idx)
    }
});