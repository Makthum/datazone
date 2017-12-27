(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('taskReportController', taskReportController);

    taskReportController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter", 'AlertService'];

    function taskReportController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter, AlertService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        getEquipments();
        getSchedules();
        getPersonnels();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        };

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


        vm.load = function() {
            var dataset = {
                'fromDate': vm.fromDate,
                'toDate': vm.toDate,
                'page': 0,
                'size': 5000
            };
            $http({
                method: 'POST',
                url: '/api/tasks/report',
                data: dataset,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.tasks = response.data;
                setDates(vm.tasks);
                AlertService.success(response.statusText);
            }, function myError(response) {
                vm.composition = {};
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
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
                if (tasks[i].startDate)
                    tasks[i].startDate = new Date(tasks[i].startDate);
                if (tasks[i].endDate)
                    tasks[i].endDate = new Date(tasks[i].endDate);
            }
        }

        vm.print = function(task) {
            var url = $state.href('taskDetail', { taskId: task });
            var wind = window.open(url, '_blank');
            wind.onload = function() {
                var isIE = /(MSIE|Trident\/|Edge\/)/i.test(navigator.userAgent);
                if (isIE) {

                    wind.print();
                    setTimeout(function() { wind.close(); }, 100);

                } else {

                    setTimeout(function() {
                        wind.print();
                        var ival = setInterval(function() {
                            wind.close();
                            clearInterval(ival);
                        }, 200);
                    }, 500);
                }
            }

        }



    }







})();
