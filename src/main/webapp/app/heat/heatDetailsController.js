(function() {
    'use strict';

    angular
        .module('jprApp')
        .controller('heatDetailsController', heatDetailsController);

    heatDetailsController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', "NgTableParams", "$filter", 'AlertService','$stateParams'];

    function heatDetailsController($scope, Principal, LoginService, $state, $http, NgTableParams, $filter, AlertService,$stateParams) {
        var vm = this;
        vm.heat = $stateParams.heat;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        if(vm.heat){
            loadHeatDetails(vm.heat);
        }

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
            'heatId': 99,
            'scrapName': "SilicoManganese",
            "quantity": 0
        }, {
            'heatId': 100,
            'scrapName': "FerroManganese",
            "quantity": 0
        }]

        vm.inlineOptions = {
            minDate: new Date(),
            showWeeks: true
        };

        vm.heat = {}
        vm.heatmix = {}

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

        vm.heat = {};

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
                        vm.status = response.statusText;
                        vm.error = response.statusText;
                    });
                }
            });



        vm.save = function() {
            vm.error = "";
            vm.status = "";
            $http({
                method: 'POST',
                url: '/api/heatdetails',
                data: angular.toJson(vm.heat),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.status = response.status;
                AlertService.success(response.statusText);
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
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
                AlertService.success(response.statusText);
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
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
                AlertService.success(response.statusText);
                reload();
            }, function myError(response) {
                vm.updateResult = response.data;
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
                reload();
            });
        };


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
            vm.error = "";
            vm.status = "";
            $http({
                method: 'GET',
                url: '/api/heats/recent',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.heats = response.data;
                vm.status = response.statusText;
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

        function getIssues() {
            vm.error = "";
            vm.status = "";
            $http({
                method: 'GET',
                url: '/api/issues',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function mySuccess(response) {
                vm.issues = response.data;
                vm.status = response.statusText;
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };


        vm.selectHeat = function(item) {
            loadHeatDetails(item);


        };

        vm.selectHeatType = function(item) {
            vm.heat.heatType = item;
        };

        vm.selectIssue = function(item) {
            vm.heat.issueId = item.id;
            vm.issueName = item.dimDate.date + ' - ' + item.issueDescription;
        };

        vm.furnaceOnChanged = function() {
            vm.heat.furnaceOn.setMinutes(vm.furnaceOn.getMinutes());
            vm.heat.furnaceOn.setHours(vm.furnaceOn.getHours());
            vm.heat.furnaceOn.setSeconds(vm.furnaceOn.getSeconds());
        };

        vm.furnaceOffChanged = function() {
            vm.heat.furnaceOff.setMinutes(vm.furnaceOff.getMinutes());
            vm.heat.furnaceOff.setHours(vm.furnaceOff.getHours());
            vm.heat.furnaceOff.setSeconds(vm.furnaceOff.getSeconds());
        };


        vm.addNew = function() {
            vm.rawmix.push({
                'heatId': vm.heat.id,
                'scrapName': '',
                'quantity': 0,
                'scrapId': ''
            })
        }

        function loadHeatDetails(item) {
            $http({
                method: 'GET',
                url: '/api/heatdetails',
                params: { id: parseInt(item.id) }
            }).then(function mySuccess(response) {
                    if (response.data) {
                        vm.heat = response.data;
                        vm.heat.furnaceOn = new Date(vm.heat.furnaceOn);
                        vm.heat.furnaceOff = new Date(vm.heat.furnaceOff);
                        vm.furnaceOn = vm.heat.furnaceOn;
                        vm.furnaceOff = vm.heat.furnaceOff;
                    } else {
                        vm.heat = {};
                    }
                    vm.status = response.statusText;
                    setHeatValues(item);
                },
                function myError(response) {
                    AlertService.error(response.data.detail);
                    AlertService.error(response.statusText);
                });

            $http({
                method: 'GET',
                url: '/api/heatdetails/heatmix',
                params: { id: parseInt(item.id) }
            }).then(function mySuccess(response) {
                if (response.data) {
                    vm.heatmix = response.data;
                } else {
                    vm.heatmix = {};
                }
                vm.status = response.statusText;
                setHeatValues(item);
            }, function myError(response) {
                AlertService.error(response.data.detail);
                AlertService.error(response.statusText);
            });
        };

        function setHeatValues(item) {
            vm.heat.heatId = parseInt(item.id);
            vm.heatmix.dimHeatId = parseInt(item.id);
            if (!vm.heat.furnaceOn) {
                vm.heat.furnaceOn = new Date(item.dimDateOn.date);
            }
            if (!vm.heat.furnaceOff) {
                vm.heat.furnaceOff = new Date(item.dimDateOff.date);
            }
            vm.heatName = item.heatId + "-" + item.sinteringHeatId;
        }

        vm.searchHeat = function(id) {
            $http({
                method: 'GET',
                url: '/api/heats/'+id,
            }).then(function mySuccess(response) {
                    if (response.data) {
                        loadHeatDetails(response.data)
                    } else {
                        vm.heat = {};
                    }
                    vm.status = response.statusText;
                },
                function myError(response) {
                    AlertService.error(response.data.detail);
                    AlertService.error(response.statusText);
                });
        }


    }







})();
