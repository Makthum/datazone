(function() {
    'use strict';

    angular
        .module('jprApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('ScrapReceivedForm',{
        	parent: 'app',
        	url:'/scrapReceivedForm',
        	data: {
        		authorities: []
        	},
        	views: {
        		'content@': {
                    templateUrl: 'app/scrap/scrapReceivedForm.html',
                    controller: 'scrapReceivedController',
                    controllerAs: 'vm'
        	}
        	}
        }).
        state('CreateScrapForm', {
            parent: 'app',
            url:'/createScrapForm',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/scrap/createscrapform.html',
                    controller: 'createScrapController',
                    controllerAs: 'vm'
            }
            }
        })
        .state('ScrapLogs', {
            parent: 'app',
            url:'/ScrapReceivedLogs',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/scrap/ScrapReceivedLogs.html',
                    controller: 'ScrapReceivedLogsController',
                    controllerAs: 'vm'
            }
            }
        }).state('ScrapReceivedReport', {
            parent: 'app',
            url:'/ScrapReceivedReport',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/scrap/ScrapReceivedReport.html',
                    controller: 'ScrapReceivedReportController',
                    controllerAs: 'vm'
            }
            }
        }).state('ScrapIssuedReport', {
            parent: 'app',
            url:'/ScrapIssuedReport',
            data: {
                authorities: []
            },
            params : {
                fromDate: null,
                toDate: null
            },
            views: {
                'content@': {
                    templateUrl: 'app/scrap/ScrapIssuedReport.html',
                    controller: 'ScrapIssuedReportController',
                    controllerAs: 'vm'
            }
            }
        }).state('ScrapIssueForm', {
            parent: 'app',
            url:'/ScrapIssueForm',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/scrap/ScrapIssuedForm.html',
                    controller: 'ScrapIssuedController',
                    controllerAs: 'vm'
            }
            }
        }).state('ScrapMonthlyBalance', {
            parent: 'app',
            url:'/ScrapMonthlyBalance',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/scrap/ScrapMonthlyBalanceReport.html',
                    controller: 'ScrapMonthlyBalanceController',
                    controllerAs: 'vm'
            }
            }
        });
    }
})();