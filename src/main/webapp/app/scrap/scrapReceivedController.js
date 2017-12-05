(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('scrapReceivedController', scrapReceivedController);

    scrapReceivedController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter"];

    function scrapReceivedController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter) {
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

        vm.items = [{
            'date': new Date(),
            'scrapType': '',
            'quantity': vm.Quantity,
            'opened': false,
            'scrapTypeName': ''
        }]

        vm.addNew = function() {
            vm.items.push({
                'date': new Date(),
                'scrapType': '',
                'quantity': 0,
                'opened': false
            })
        }
        vm.open1 = function(count) {
            vm.items[count].opened = true;
        };

        vm.selectItem = function(id, name, index) {
            vm.items[index].scrapType = id;
            vm.items[index].scrapTypeName = name;
        }




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
            $http({
                method: 'POST',
                url: '/api/scrap/received',
                data: angular.toJson(vm.items),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.status = response.data;
            }, function myError(response) {
                vm.status = response.data;
            });


        };

        function reset() {
            vm.items = [];
        }

        function getScrapTypes() {
            $http({
                    method: 'GET',
                    url: '/api/scrap/types',
                })
                .then(function mySuccess(response) {
                    vm.scrapTypes = response.data;
                }, function myError(response) {
                    vm.error = response.statusText;
                });
        };
    }







})();
