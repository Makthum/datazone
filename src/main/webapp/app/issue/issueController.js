(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('issueController', issueController);

    issueController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter",'AlertService'];

    function issueController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter,AlertService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        getScrapTypes();

        getComponents();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

        vm.today = function() {
            vm.dt = new Date();
        };

        vm.clear = function() {
            vm.dt = null;
        };

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
        vm.open1 = function(count) {
            vm.dateOpened = true;
        };

        vm.selectItem = function(item) {
            vm.issue.componentId = parseInt(item.id);
            vm.componentName = item.department + "-" + item.component + "-" + item.subComponent;
        }


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
                        vm.status = response.statusText;
                        return vm.tabledata;
                    }, function myError(response) {
                        vm.error = response.data.detail;
                        vm.status = response.statusText;
                    });
                }
            });




        vm.setDate = function(year, month, day) {
            vm.dt = new Date(year, month, day);
        };

        vm.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        vm.format = vm.formats[0];
        vm.altInputFormats = ['M!/d!/yyyy'];

        vm.popup1 = {
            opened: false
        };

        vm.save = function() {
            vm.error = "";
            vm.status= "";
            $http({
                method: 'POST',
                url: '/api/issues',
                data: angular.toJson(vm.issue),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.error = response.data.detail;
                AlertService.success(response.statusText);
                reload();
            }, function myError(response) {
                vm.error = response.data.detail;
                AlertService.error(response.statusText);
                reload();
            });


        };

        function reset() {
            vm.items = [];
        }

        function reload() {
            vm.error = "";
            vm.status= "";
            vm.tableParams.reload();
        }

        function getScrapTypes() {
            return $http({
                method: 'GET',
                url: '/api/issues',
            });
        };

        function getComponents() {
            $http({
                    method: 'GET',
                    url: '/api/components',
                })
                .then(function mySuccess(response) {
                    vm.components = response.data;
                    vm.status = response.statusText;
                }, function myError(response) {
                    vm.error = response.data.detail;
                    vm.status = response.statusText;
                });
        };
    }







})();
