(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('ScrapIssuedReportController', ScrapIssuedReportController);

    ScrapIssuedReportController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', 'NgTableParams', '$filter', "$stateParams", 'AlertService'];

    function ScrapIssuedReportController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter, $stateParams, AlertService) {
        var vm = this;
        vm.fromDate = $stateParams.fromDate;
        vm.toDate = $stateParams.toDate;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

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

        vm.disableLoad = true;

        vm.tableParams = new NgTableParams({
                page: 1,
                count: 10
            },

            {
                getData: function(params) {
                    //code to fetch data that matches the params values EG: 
                    if (vm.disableLoad)
                        return;
                    return getScraplogs(params.page(), params.count()).then(function mySuccess(response) {
                        vm.tabledata = response.data;
                        vm.tabledata = params.sorting() ? $filter('orderBy')(vm.tabledata, params.orderBy()) : vm.tabledata;
                        vm.tabledata = params.filter() ? $filter('filter')(vm.tabledata, params.filter()) : vm.tabledata;
                        vm.tabledata = vm.tabledata.slice((params.page() - 1) * params.count(), params.page() * params.count());
                        vm.status = response.statusText;
                        params.total(response.data.length);
                        return vm.tabledata;
                    }, function myError(response) {
                        AlertService.error(response.data.detail);
                        AlertService.error(response.statusText);
                    });
                }
            });


        if (vm.fromDate != null && vm.toDate != null) {
            vm.disableLoad = false;
            vm.tableParams.reload();
        }


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



        function getScraplogs(page, count) {
            vm.error = "";
            vm.status = "";
            var dataset = {
                'fromDate': vm.fromDate,
                'toDate': vm.toDate,
                'page': page - 1,
                'size': 5000
            }
            return $http({
                method: 'POST',
                url: '/api/scrap/issuedreport',
                data: dataset
            });
        };

        vm.load = function() {
            vm.error = "";
            vm.status = "";
            vm.disableLoad = false;
            vm.tableParams.reload();
        }

        vm.download = function() {
            vm.error = "";
            vm.status = "";
            var dataset = {
                'fromDate': vm.fromDate,
                'toDate': vm.toDate,
                'page': 1,
                'size': 5000
            };
            $http({
                method: 'POST',
                url: '/api/scrap/issuedreportdownload',
                data: dataset,
                header: {
                    'Content-type': 'text/csv'
                }
            }).then(function(data, status, headers, config) {
                    var anchor = angular.element('<a/>');
                    anchor.attr({
                        href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data.data),
                        target: '_blank',
                        download: 'issuedReport.csv'
                    })[0].click();

                },
                function myError(response) {
                    AlertService.error(response.data.detail);
                    AlertService.error(response.statusText);
                });
        }
    }







})();
