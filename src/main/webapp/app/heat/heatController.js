(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('heatController', heatController);

    heatController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter","AlertService"];

    function heatController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter,AlertService) {
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
                AlertService.success(response.statusText);
                reload();
            }, function myError(response) {
               AlertService.error(response.statusText);
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

        function reload() {
            vm.error = "";
            vm.status= "";
            vm.tableParams.reload();
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

        vm.disableLoad = true;






        vm.open1 = function(count) {
            vm.opened1 = true;
        };

        vm.open2 = function(count) {
            vm.opened2 = true;
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

        vm.load = function() {
            var dataset = {
                'fromDate': vm.fromDate,
                'toDate': vm.toDate,
                'page': 0,
                'size': 5000
            };
            $http({
                method: 'POST',
                url: '/api/heats/report',
                data: dataset,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.heats = response.data;
                AlertService.success(response.statusText);
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

    }







})();
