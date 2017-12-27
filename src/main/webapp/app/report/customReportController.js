(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('customReportController', customReportController);

    customReportController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', 'NgTableParams', '$filter', 'AlertService'];

    function customReportController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter, AlertService) {
        var vm = this;

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


        vm.load = function() {
            var dataset = {
                'fromDate': vm.fromDate,
                'toDate': vm.toDate,
                'page': 0,
                'size': 5000
            };
            $http({
                method: 'POST',
                url: '/api/reports/sql',
                data: vm.query,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.data = response.data;
                AlertService.success(response.statusText);
            }, function myError(response) {
                vm.composition = {};
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

          vm.download = function() {
            vm.error = "";
            vm.status = "";
            var dataset = {
                'fromDate': vm.fromDate,
                'toDate': vm.toDate,
                'page': 0,
                'size': 5000
            };
            $http({
                method: 'POST',
                url: '/api/reports/download/sql',
                data: vm.query,
                header: {
                    'Content-type': 'text/csv'
                }
            }).then(function(data, status, headers, config) {
                    var anchor = angular.element('<a/>');
                    anchor.attr({
                        href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data.data),
                        target: '_blank',
                        download: 'monthlyBalance.csv'
                    })[0].click();

                },
                function myError(response) {
                    AlertService.error(response.data.detail);
                    AlertService.error(response.statusText);
                });
        }
    }







})();
