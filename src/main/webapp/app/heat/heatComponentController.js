(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('heatComponentController', heatComponentController);

    heatComponentController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter",'AlertService'];

    function heatComponentController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter,AlertService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        };

        vm.items= {};
        vm.updateitems={}


        vm.tableParams = new NgTableParams({
                page: 1,
                count: 10
            },

            {
                getData: function(params) {
                    //code to fetch data that matches the params values EG: 
                    return getScrapTypes().then(function mySuccess(response) {
                        vm.tabledata = response.data;
                        vm.tabledata = params.sorting() ? $filter('orderBy')(vm.tabledata, params.orderBy()) : vm.tabledata;
                        vm.tabledata = params.filter() ? $filter('filter')(vm.tabledata, params.filter()) : vm.tabledata;
                        vm.tabledata = vm.tabledata.slice((params.page() - 1) * params.count(), params.page() * params.count());
                        params.total(response.data.length);
                        return vm.tabledata;
                    }, function myError(response) {
                        vm.error = response.statusText;
                    });
                }
            });



        vm.save = function() {
            vm.error = "";
            vm.status= "";
            $http({
                method: 'POST',
                url: '/api/components',
                data: angular.toJson(vm.items),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                reload();
            }, function myError(response) {
                AlertService.error(esponse.statusText);
                reload();
            });
        };

        vm.update = function() {
            vm.error = "";
            vm.status= "";
            var putdata = {
                "id": vm.updateId,
                "name": vm.updateName
            };
            $http({
                method: 'PUT',
                url: '/api/components',
                data: vm.updateitems,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.updateResult = response.data;
                AlertService.success(response.statusText);
                reload();
            }, function myError(response) {
                vm.error = response.data.detail;
                AlertService.error(response.statusText);
                reload();
            });
        };



        function getScrapTypes() {
            return $http({
                method: 'GET',
                url: '/api/components',
            });
        };

        function reload() {
            vm.tableParams.reload();
        }

    }







})();
