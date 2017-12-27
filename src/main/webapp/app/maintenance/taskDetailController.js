(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('taskDetailController', taskDetailController);

    taskDetailController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter", 'AlertService', '$stateParams'];

    function taskDetailController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter, AlertService, $stateParams) {
        var vm = this;

        vm.taskId = $stateParams.taskId;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        getTasks();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        };


       vm.task={};
        vm.taskTypes = [{
            'name': 'repair',
            'id': 'repair'
        }, {
            'name': 'Maintenance',
            'id': 'Maintenance'
        }]

        vm.statuses = [{
            'name': 'OPEN'
        }, {
            'name': 'CLOSED'
        }, {
            'name': 'WORKING'
        }, {
            'name': 'WAITING'
        }]


        vm.save = function() {
            vm.error = "";
            vm.status = "";
            $http({
                method: 'POST',
                url: '/api/tasks',
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
                url: '/api/tasks',
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

        function getTasks() {
            $http({
                method: 'GET',
                url: '/api/tasks/' + vm.taskId,
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                vm.task = response.data;
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

        

        

       


    }







})();
