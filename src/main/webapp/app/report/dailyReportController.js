(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('dailyReportController', dailyReportController);

    dailyReportController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter"];

    function dailyReportController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.date = new Date();
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        vm.fromDate = new Date();
        vm.toDate = new Date();

        getAccount();

        getReport();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
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


        vm.open1 = function() {
            vm.dateOpened1 = true;
        };

         vm.open2 = function() {
            vm.dateOpened2 = true;
        };

        vm.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        vm.format = vm.formats[0];
        vm.altInputFormats = ['M!/d!/yyyy'];

        vm.popup1 = {
            opened: false
        };



        vm.load = function() {
            getReport();
        };


        function getReport() {
            var dataset = {
                'fromDate': vm.fromDate,
                'toDate': vm.toDate,
                'page': 0,
                'size': 5000
            };
            vm.error = "";
            vm.status= "";
            $http({
                method: 'POST',
                url: '/api/reports/daily',
                data: dataset,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.details = response.data;
                vm.status = response.statusText;
            }, function myError(response) {
                vm.details = {};
                vm.error = response.data.detail;
                vm.status = response.statusText;
            });

            $http({
                method: 'POST',
                url: '/api/reports/daily/composition',
                data: dataset,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.composition = response.data;
                vm.status = response.statusText;
            }, function myError(response) {
                vm.composition = {};
                vm.error = response.data.detail;
                vm.status = response.statusText;
            });

            $http({
                method: 'POST',
                url: '/api/reports/daily/cost',
                data: dataset,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.costComposition = response.data;
                vm.status = response.statusText;
            }, function myError(response) {
                vm.composition = {};
                vm.error = response.data.detail;
                vm.status = response.statusText;
            });
        };



    }







})();
