(function() {
    'use strict';

    angular
        .module('jprApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('dailyReport', {
            parent: 'app',
            url: '/dailyReport',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/report/dailyReport.html',
                    controller: 'dailyReportController',
                    controllerAs: 'vm'
                }
            }
        }).state('issueReport', {
            parent: 'app',
            url: '/issueReport',
            params : {
                fromDate: null,
                toDate: null
            },
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/report/issueReport.html',
                    controller: 'issueReportController',
                    controllerAs: 'vm'
                }
            }
        }).state('monthlyReceivedReport', {
            parent: 'app',
            url: '/monthlyReceivedReport',
            params : {
                fromDate: null,
                toDate: null
            },
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/report/monthlyScrapReceived.html',
                    controller: 'monthlyScrapReceivedController',
                    controllerAs: 'vm'
                }
            }
        }).state('monthlyIssuedReport', {
            parent: 'app',
            url: '/monthlyIssuedReport',
            params : {
                fromDate: null,
                toDate: null
            },
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/report/monthlyScrapIssued.html',
                    controller: 'monthlyScrapIssuedController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
