(function() {
    'use strict';

    angular
        .module('jprApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('createEquipment', {
            parent: 'app',
            url: '/createEquipment',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/maintenance/createEquipment.html',
                    controller: 'createEquipmentController',
                    controllerAs: 'vm'
                }
            }
        }).state('createPersonnel', {
            parent: 'app',
            url: '/createPersonnel',
            params: {
                fromDate: null,
                toDate: null
            },
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/maintenance/createPersonnel.html',
                    controller: 'createPersonnelController',
                    controllerAs: 'vm'
                }
            }
        }).state('createSchedule', {
            parent: 'app',
            url: '/createSchedule',
            params: {
                fromDate: null,
                toDate: null
            },
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/maintenance/createSchedule.html',
                    controller: 'createScheduleController',
                    controllerAs: 'vm'
                }
            }
        }).state('createTask', {
            parent: 'app',
            url: '/createTask',
            params: {
                fromDate: null,
                toDate: null
            },
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/maintenance/createTask.html',
                    controller: 'createTaskController',
                    controllerAs: 'vm'
                }
            }
        }).state('taskDetail', {
            parent: 'app',
            url: '/taskDetail/{taskId}',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/maintenance/taskDetail.html',
                    controller: 'taskDetailController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
