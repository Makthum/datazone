(function() {
    'use strict';

    angular
        .module('jprApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('heatComponentForm', {
            parent: 'app',
            url: '/heatComponentForm',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/heat/heatComponentForm.html',
                    controller: 'heatComponentController',
                    controllerAs: 'vm'
                }
            }
        }).state('heatForm', {
            parent: 'app',
            url: '/heatForm',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/heat/heatForm.html',
                    controller: 'heatController',
                    controllerAs: 'vm'
                }
            }
        }).state('heatDetails', {
            parent: 'app',
            url: '/heatDetails',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/heat/heatDetails.html',
                    controller: 'heatDetailsController',
                    controllerAs: 'vm'
                }
            }
        }).state('heatReport', {
            parent: 'app',
            url: '/heatReport',
            params : {
                fromDate : null,
                toDate: null
            },
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/heat/heatReport.html',
                    controller: 'heatReportController',
                    controllerAs: 'vm'
                }
            }
        });;
    }
})();
