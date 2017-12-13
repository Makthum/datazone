(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('createScrapController', createScrapController);

    createScrapController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter"];

    function createScrapController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter) {
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

        getId();

        vm.items = [];
        vm.cost = {};

        vm.inlineOptions = {
            minDate: new Date(),
            showWeeks: true
        };

        vm.dateOptions = {
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            minDate: new Date(2016, 1, 1),
            startingDay: 1
        };

        vm.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        vm.format = vm.formats[0];
        vm.altInputFormats = ['M!/d!/yyyy'];
        vm.popup1 = {
            opened: false
        };

        vm.tableParams = new NgTableParams({
                page: 1,
                count: 10
            },

            {
                getData: function(params) {
                    //code to fetch data that matches the params values EG: 
                    return getScrapTypes().then(function mySuccess(response) {
                        vm.cost.scraps = response.data;
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




        vm.addNew = function() {
            vm.items.push({
                'id': vm.currentId++,
                'name': " "
            })
        };

        vm.save = function() {
            $http({
                method: 'POST',
                url: '/api/scrap/type',
                data: angular.toJson(vm.items),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                reload();
            }, function myError(response) {
                reload();
            });
        };

        vm.saveCost = function() {
            $http({
                method: 'POST',
                url: '/api/cost',
                data: angular.toJson(vm.cost),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                reload();
            }, function myError(response) {
                reload();
            });
        };

        vm.update = function() {

            var putdata = {
                "id": vm.updateId,
                "name": vm.updateName
            };
            $http({
                method: 'PUT',
                url: '/api/scrap/types',
                data: putdata,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.updateResult = response.data;
                reload();
            }, function myError(response) {
                vm.updateResult = response.data;
                reload();
            });
        };

        function getId() {
            $http({
                method: 'GET',
                url: '/api/scrap/getid',
            }).then(function mySuccess(response) {
                vm.maxId = response.data;
                vm.currentId = parseInt(vm.maxId) + 1;
            }, function myError(response) {
                vm.error = response.statusText;
            });

        };

        function getScrapTypes() {
            return $http({
                method: 'GET',
                url: '/api/scrap/types',
            });
        };

        function reload() {
            vm.tableParams.reload();
        }

        vm.selectCostScrap = function(item) {
            vm.cost.scrapId = item.id;
            vm.cost.scrapName = item.name;
        }

        vm.open1 = function() {
            vm.opened1 = true;
        };

    }







})();
