(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('heatController', heatController);

    heatController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter"];

    function heatController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter) {
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

        vm.items = {};
        vm.updateitems = {}

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

        vm.issue = {};
        vm.issueUpdate = {}
        vm.componentName = "";

        vm.addNew = function() {
            vm.items.push({
                'date': new Date(),
                'scrapType': '',
                'quantity': 0,
                'opened': false
            })
        }
        vm.open1 = function() {
            vm.dateOpened = true;
        };

        vm.open2 = function() {
            vm.dateOpened2 = true;
        };

        vm.open3 = function() {
            vm.dateOpened3 = true;
        };

        vm.open4 = function() {
            vm.dateOpened4 = true;
        };

        vm.setDate = function(year, month, day) {
            vm.dt = new Date(year, month, day);
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
                url: '/api/heats',
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

        vm.update = function() {
            vm.error = "";
            vm.status= "";
            $http({
                method: 'PUT',
                url: '/api/heats',
                data: angular.toJson(vm.updateItems),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                reload();
            }, function myError(response) {
                reload();
            });
        };



        function getScrapTypes() {
            return $http({
                method: 'GET',
                url: '/api/heats',
            });
        };

        function reload() {
            vm.error = "";
            vm.status= "";
            vm.tableParams.reload();
        }

    }







})();
