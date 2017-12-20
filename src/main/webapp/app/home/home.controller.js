(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http'];

    function HomeController($scope, Principal, LoginService, $state, $http) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        getGraph();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

        vm.runningTime = {};
        vm.upt={};
        vm.breakdown={};
        vm.maximumDemand={};
        vm.powerfactor ={};

        var chart = {
            "caption": "Production Vs Running Time",
            "plotgradientcolor": "",
            "outputdateformat": "dd",
            "bgcolor": "FFFFFF",
            "showalternatehgridcolor": "0",
            "divlinecolor": "CCCCCC",
            "showvalues": "0",
            "showcanvasborder": "0",
            "canvasborderalpha": "0",
            "canvasbordercolor": "CCCCCC",
            "canvasborderthickness": "1",
            "yaxismaxvalue": "300",
            "captionpadding": "30",
            "linethickness": "3",
            "yaxisvaluespadding": "15",
            "legendshadow": "0",
            "legendborderalpha": "0",
            "palettecolors": "#f8bd19,#008ee4,#33bdda,#e44a00,#6baa01,#583e78",
            "showborder": "0"
        };


        vm.runningTime.chart= angular.copy(chart);
        vm.upt.chart=angular.copy(chart);
        vm.breakdown.chart= angular.copy(chart);
        vm.maximumDemand.chart= angular.copy(chart);
        vm.powerfactor.chart= angular.copy(chart);




        function getGraph() {
            $http({
                    method: 'GET',
                    url: '/api/graphs/monthly',
                    params: { 'fields': ['production', 'RunningTime'] }
                })
                .then(function mySuccess(response) {
                    vm.runningTime.dataset = response.data.dataset;
                    vm.runningTime.categories = response.data.categories;
                    vm.runningTime.chart.caption = "Production Vs Running Time";
                }, function myError(response) {
                    vm.error = response.data.detail;
                    vm.status = response.statusText;
                });

                $http({
                    method: 'GET',
                    url: '/api/graphs/monthly',
                    params: { 'fields': ['production', 'upt'] }
                })
                .then(function mySuccess(response) {
                    vm.upt.dataset = response.data.dataset;
                    vm.upt.categories = response.data.categories;
                    vm.upt.chart.caption = "Production Vs UPT";
                }, function myError(response) {
                    vm.error = response.data.detail;
                    vm.status = response.statusText;
                });

                $http({
                    method: 'GET',
                    url: '/api/graphs/monthly',
                    params: { 'fields': ['production', 'breakdown'] }
                })
                .then(function mySuccess(response) {
                    vm.breakdown.dataset = response.data.dataset;
                    vm.breakdown.categories = response.data.categories;
                    vm.breakdown.chart.caption = "Production Vs BreakDown";
                }, function myError(response) {
                    vm.error = response.data.detail;
                    vm.status = response.statusText;
                });

                $http({
                    method: 'GET',
                    url: '/api/graphs/monthly',
                    params: { 'fields': ['production', 'maximumDemand'] }
                })
                .then(function mySuccess(response) {
                    vm.maximumDemand.dataset = response.data.dataset;
                    vm.maximumDemand.categories = response.data.categories;
                    vm.maximumDemand.chart.caption = "Production Vs Operating Power";
                }, function myError(response) {
                    vm.error = response.data.detail;
                    vm.status = response.statusText;
                });
                $http({
                    method: 'GET',
                    url: '/api/graphs/monthly',
                    params: { 'fields': ['production', 'powerFactor'] }
                })
                .then(function mySuccess(response) {
                    vm.powerfactor.dataset = response.data.dataset;
                    vm.powerfactor.categories = response.data.categories;
                    vm.powerfactor.chart.caption = "Production Vs Power Factor";
                }, function myError(response) {
                    vm.error = response.data.detail;
                    vm.status = response.statusText;
                });
        };
    }
})();
