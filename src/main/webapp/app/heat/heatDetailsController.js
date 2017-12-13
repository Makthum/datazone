(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('heatDetailsController', heatDetailsController);

    heatDetailsController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter"];

    function heatDetailsController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        getHeats();
        getIssues();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        };

        vm.items = {};
        vm.updateitems = {}
        vm.rawmix = [{
            'heatId' : 99,
            'scrapName' : "SilicoManganese",
            "quantity" : 0
        },{
            'heatId' : 100,
            'scrapName' : "FerroManganese",
            "quantity" : 0
        }]

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

        vm.issue = {};
        vm.issueUpdate = {}
        vm.componentName = "";

        vm.addNew = function() {
            vm.items.push({
                'date': new Date(),
                'scrapType': '',
                'quantity': 0,
                'opened': false
            })
        }
        vm.open1 = function(count) {
            vm.dateOpened = true;
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



        vm.tableParams = new NgTableParams({
                page: 1,
                count: 10
            },

            {
                getData: function(params) {
                    //code to fetch data that matches the params values EG: 
                    return getScrapTypes().then(function mySuccess(response) {
                        vm.tabledata = response.data;
                        vm.tabledata = params.sorting() ? $filter('orderBy')(vm.tabledata, params.orderBy()) : vm.tabledata;
                        vm.tabledata = params.filter() ? $filter('filter')(vm.tabledata, params.filter()) : vm.tabledata;
                        vm.tabledata = vm.tabledata.slice((params.page() - 1) * params.count(), params.page() * params.count());
                        params.total(response.data.length);
                        return vm.tabledata;
                    }, function myError(response) {
                        vm.error = response.statusText;
                    });
                }
            });



        vm.save = function() {
            vm.error = "";
            $http({
                method: 'POST',
                url: '/api/heatdetails',
                data: angular.toJson(vm.heat),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.status = response.status;
            }, function myError(response) {
                vm.error = response.data.detail;
            });

            $http({
                method: 'POST',
                url: '/api/heatdetails/heatmix',
                data: angular.toJson(vm.heatmix),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.status = response.status;
            }, function myError(response) {
                vm.error = response.data.detail;
            });
        };

        vm.update = function() {

            var putdata = {
                "id": vm.updateId,
                "name": vm.updateName
            };
            $http({
                method: 'PUT',
                url: '/api/heatdetails',
                data: vm.updateitems,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.updateResult = response.data;
                reload();
            }, function myError(response) {
                vm.updateResult = response.data;
                reload();
            });
        };


        vm.heat = {
            'furnaceOff': new Date(),
            'furnaceOn': new Date()
        }

        vm.heatmix = {};

        function getScrapTypes() {
            return $http({
                method: 'GET',
                url: '/api/heatdetails',
            });
        };

        function reload() {
            vm.tableParams.reload();
        }

        vm.mytime = new Date();

        vm.hstep = 1;
        vm.mstep = 15;

        vm.options = {
            hstep: [1, 2, 3],
            mstep: [1, 5, 10, 15, 25, 30]
        };

        vm.ismeridian = true;

        function getHeats() {
            $http({
                method: 'GET',
                url: '/api/heats/recent',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.heats = response.data;
            }, function myError(response) {
                vm.status = response.error;
            });
        };

        function getIssues() {
            $http({
                method: 'GET',
                url: '/api/issues',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.issues = response.data;
            }, function myError(response) {
                vm.status = response.error;
            });
        };


        vm.selectHeat = function(item) {
            vm.heat.heatId = parseInt(item.id);
            vm.heatmix.dimHeatId = parseInt(item.id);
            vm.heatName = item.heatId + "-" + item.sinteringHeatId;
        };

        vm.selectHeatType = function(item) {
            vm.heat.heatType = item;
        };

        vm.selectIssue = function(item) {
            vm.heat.issueId = item.id;
            vm.issueName = item.dimDate.date + ' - ' + item.issueDescription;
        };

        vm.furnaceOnchanged = function() {
            vm.heat.furnaceOn = vm.furnaceOn;
        };

        vm.furnaceOffchanged = function() {
            vm.heat.furnaceOff = vm.furnaceOff;
        };


        vm.addNew = function() {
            vm.rawmix.push({
                'heatId': vm.heat.id,
                'scrapName': '',
                'quantity': 0,
                'scrapId': ''
            })
        }
    }







})();
