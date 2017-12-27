(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('createTaskController', createTaskController);

    createTaskController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter", 'AlertService'];

    function createTaskController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter, AlertService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        getTasks();
        getEquipments();
        getSchedules();
        getPersonnels();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        };


        vm.items = {};
        vm.cost = {};
        vm.schedules = [];
        vm.personnelIds = [];
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
        }, {
            'name': 'INVALID'
        }, {
            'name': 'NOTDONE'
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
                url: '/api/tasks',
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                vm.tasks = response.data;
                setDates(vm.tasks);
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

        vm.updateTasks = function() {
            $http({
                method: 'GET',
                url: '/api/tasks/update',
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                vm.tasks = response.data;
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

        function getEquipments() {
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

        function getSchedules() {
            $http({
                method: 'GET',
                url: '/api/schedules',
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                vm.schedules = response.data;
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

        function getPersonnels() {
            $http({
                method: 'GET',
                url: '/api/personnels',
            }).then(function mySuccess(response) {
                AlertService.success(response.statusText);
                vm.personnels = response.data;
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };


        function reload() {
            vm.error = "";
            vm.status = "";
            getSchedules();
        }

        vm.showStatus = function(data) {
            var selected = [];
            if (!vm.personnels)
                return 'Not set';
            for (var j = 0; j < vm.personnels.length; j++) {
                var found = false;
                for (var i = 0; i < data.personnels.length; i++) {
                    if (data.personnels[i].id == vm.personnels[j].id) {
                        selected.push(data.personnels[i].name);
                        break;
                    }
                }
            };
            return selected.length ? selected.join(', ') : 'Not set';
        };

        vm.show = function(item) {
            console.log(item);
        }

        function setDates(tasks) {
            for (var i = 0; i < tasks.length; i++) {
                tasks[i].startDate = new Date(tasks[i].startDate);
                tasks[i].endDate = new Date(tasks[i].endDate);
            }
        }



    }







})();
