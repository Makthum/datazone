(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('createEquipmentController', createEquipmentController);

    createEquipmentController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter", 'AlertService'];

    function createEquipmentController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter, AlertService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
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
        };


        vm.items = {};
        vm.cost = {};
        vm.equipments = {};

        vm.save = function() {
            vm.error = "";
            vm.status = "";
            $http({
                method: 'POST',
                url: '/api/equipments',
                data: vm.items,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                reload();
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
                reload();
            });
        };

        vm.update = function(dat) {
            vm.error = "";
            vm.status = "";
            $http({
                method: 'POST',
                url: '/api/equipments',
                data: dat,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                reload();
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
                reload();
            });
        };

        function getScrapTypes() {
            $http({
                method: 'GET',
                url: '/api/equipments',
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                vm.equipments = response.data;
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

        function reload() {
            vm.error = "";
            vm.status = "";
            getScrapTypes();
        }


        

        
    }







})();
